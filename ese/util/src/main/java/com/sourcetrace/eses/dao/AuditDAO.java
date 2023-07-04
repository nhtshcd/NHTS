package com.sourcetrace.eses.dao;

import org.apache.poi.ss.formula.functions.*;
import org.apache.tools.ant.taskdefs.*;
import org.hibernate.*;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import javax.transaction.*;
import java.io.Serializable;
import java.lang.*;
import java.lang.InstantiationException;
import java.util.*;
import java.util.stream.Collectors;
@Repository
@Transactional
public class AuditDAO extends ESEDAO implements IAuditDAO {
    @Autowired
    public AuditDAO(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }
    public <T> List<T> getAuditRecordsWithRelations(Class<T> entityClass, List<String> relationNames, Long entityId) {
        AuditReader auditReader = AuditReaderFactory.get(getSessionFactory().getCurrentSession());

        List<Object[]> resultList = auditReader.createQuery()
                .forRevisionsOfEntity(entityClass, false, true)
                .add(AuditEntity.id().eq(entityId))
                .getResultList();

        Map<Class<?>, List<Object>> relatedEntitiesMap = extractRelatedEntities(resultList, relationNames,entityClass);

        return resultList.stream()
                .map(result -> mapToEntityWithRelations(entityClass, result, relatedEntitiesMap,relationNames))
                .collect(Collectors.toList());
    }

    private Map<Class<?>, List<Object>> extractRelatedEntities(List<Object[]> resultList, List<String> relationNames,Class entityClass) {
        return relationNames.stream()
                .collect(Collectors.toMap(
                        relationName -> getFieldType(entityClass, relationName),
                        relationName -> fetchRelatedEntities(entityClass, relationName, resultList,relationNames)
                ));
    }

    private List<Object> fetchRelatedEntities(Class<?> entityClass, String relationName, List<Object[]> resultList,List<String> relationNames) {
        Class<?> relationClass = getFieldType(entityClass, relationName);

        return resultList.stream()
                .map(result -> result[getIndexForRelation(entityClass, relationNames, relationName)])
                .filter(relatedEntity -> relatedEntity != null)
                .map(relatedEntity -> fetchSingleRelatedEntity(relationClass, (Long) relatedEntity))
                .collect(Collectors.toList());
    }

    private Object fetchSingleRelatedEntity(Class<?> relationClass, Long entityId) {
        AuditReader auditReader = AuditReaderFactory.get(getSessionFactory().getCurrentSession());

        Object relatedEntity = auditReader.createQuery()
                .forEntitiesAtRevision(relationClass, entityId)
                .getSingleResult();

        // Initialize lazily loaded properties or collections
        Hibernate.initialize(relatedEntity);

        return relatedEntity;
    }

    private <T> T mapToEntityWithRelations(Class<T> entityClass, Object[] result, Map<Class<?>, List<Object>> relatedEntitiesMap,List<String> relationNames) {
        T entity;
        try {
            entity = entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Failed to create instance of entity: " + entityClass.getName(), e);
        }

        relatedEntitiesMap.forEach((relationClass, relatedEntities) -> {
            Object relatedEntity = result[getIndexForRelation(entityClass, relationNames, relationClass.getSimpleName())];
            if (relatedEntity != null) {
                setRelationProperty(entity, relationClass, relatedEntities, (Serializable) relatedEntity);
            }
        });

        return entity;
    }

    private void setRelationProperty(Object entity, Class<?> relationClass, List<Object> relatedEntities, Serializable entityId) {
        try {
            Class<?> fieldType = getFieldType(entity.getClass(), relationClass.getSimpleName());
            Object relatedEntity = relatedEntities.stream()
                    .filter(e -> entityId.equals(getEntityId(e, fieldType)))
                    .findFirst()
                    .orElse(null);

            java.beans.PropertyDescriptor propertyDescriptor = new java.beans.PropertyDescriptor(relationClass.getSimpleName(), entity.getClass());
            propertyDescriptor.getWriteMethod().invoke(entity, relatedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set relation property: " + relationClass.getSimpleName(), e);
        }
    }

    private Class<?> getFieldType(Class<?> entityClass, String fieldName) {
        try {
            return entityClass.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            Class<?> superclass = entityClass.getSuperclass();
            if (superclass != null) {
                return getFieldType(superclass, fieldName);
            }
            throw new RuntimeException("Failed to get field type: " + fieldName, e);
        }
    }

    private int getIndexForRelation(Class<?> entityClass, List<String> relationNames, String relationName) {
        return entityClass.getDeclaredFields().length + relationNames.indexOf(relationName);
    }

    private Serializable getEntityId(Object entity, Class<?> fieldType) {
        try {
            java.beans.PropertyDescriptor propertyDescriptor = new java.beans.PropertyDescriptor("id", fieldType);
            return (Serializable) propertyDescriptor.getReadMethod().invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access ID field for entity: " + fieldType.getName(), e);
        }
    }
}