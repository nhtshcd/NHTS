package com.sourcetrace.eses.dao;

import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
@Component
public interface IAuditDAO {
    <T> List<T> getAuditRecordsWithRelations(Class<T> entityClass, List<String> relationNames, Long entityId);
}
