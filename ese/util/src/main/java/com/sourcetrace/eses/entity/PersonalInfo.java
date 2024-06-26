package com.sourcetrace.eses.entity;
// Generated 23 Jun, 2020 5:09:17 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.envers.*;

/**
 * PersInfo generated by hbm2java
 */
@Entity
@Table(name = "pers_info")
@Audited
public class PersonalInfo implements java.io.Serializable {
	public static final String MARITAL_STATUS_SINGLE = "SINGLE";
	public static final String MARITAL_STATUS_MARRIED = "MARRIED";
	public static final String SEX_MALE = "MALE";
	public static final String SEX_FEMALE = "FEMALE";
	private Long id;
	private String firstName;
	private String midName;
	private String lastName;
	private String secondLastName;
	private String middleName;
	private String identityType;
	private String identityNumber;
	private String sex;
	private String gender;
	private String fatherName;
	private Character salut;
	private String motherName;
	private Date dateOfBirth;
	private Character marStatus;
	private String pob;
	private String occupation;
	private String nationality;
	private byte[] image;
	private Set<User> eseUsers = new HashSet<User>(0);

	
	//Transient variable
		private String filterStatus;
		private int status;
		private File userImage;
		private String userImageString;

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	@Column(name = "FIRST_NAME",  length = 150)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "MID_NAME", length = 45)
	public String getMidName() {
		return this.midName;
	}

	public void setMidName(String midName) {
		this.midName = midName;
	}

	@Column(name = "LAST_NAME",  length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "SECOND_LAST_NAME", length = 45)
	public String getSecondLastName() {
		return this.secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	@Column(name = "MIDDLE_NAME", length = 45)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	
	@Column(name = "ID_TYPE", length = 45)
	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	@Column(name = "ID_NUMBER", length = 45)
	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	@Column(name = "SEX", length = 6)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "GENDER", length = 45)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "FATHER_NAME", length = 45)
	public String getFatherName() {
		return this.fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@Column(name = "SALUT", length = 1)
	public Character getSalut() {
		return this.salut;
	}

	public void setSalut(Character salut) {
		this.salut = salut;
	}

	@Column(name = "MOTHER_NAME", length = 45)
	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	@Column(name = "DOB")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "MAR_STATUS", length = 1)
	public Character getMarStatus() {
		return this.marStatus;
	}

	public void setMarStatus(Character marStatus) {
		this.marStatus = marStatus;
	}

	@Column(name = "POB", length = 45)
	public String getPob() {
		return this.pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	@Column(name = "OCCUPATION", length = 45)
	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "NATIONALITY", length = 45)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "IMAGE")
	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@OneToMany( mappedBy = "persInfo")
	public Set<User> getEseUsers() {
		return this.eseUsers;
	}

	public void setEseUsers(Set<User> eseUsers) {
		this.eseUsers = eseUsers;
	}

	@Transient
	public String getAgentName() {

		StringBuffer name = new StringBuffer();
		if (firstName != null) {
			name.append(firstName);
			name.append(" ");
		}
		if (lastName != null) {
			name.append(lastName);
		}
		return name.toString();
	}
	@Transient
	public String getName() {

		StringBuffer name = new StringBuffer();
		if (firstName != null) {
			name.append(firstName);
			name.append(" ");
		}
		if (lastName != null) {
			name.append(lastName);
		}
		if (middleName != null) {
			name.append(middleName);
			name.append(" ");
		}
		return name.toString();
	}
	@Transient
	public String getFilterStatus() {
		return filterStatus;
	}

	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}
	@Transient
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Transient
	public File getUserImage() {
		return userImage;
	}

	public void setUserImage(File userImage) {
		this.userImage = userImage;
	}
	@Transient
	public String getUserImageString() {
		return userImageString;
	}

	public void setUserImageString(String userImageString) {
		this.userImageString = userImageString;
	}


}
