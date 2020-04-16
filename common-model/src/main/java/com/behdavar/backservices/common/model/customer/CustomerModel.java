package com.behdavar.backservices.common.model.customer;

import com.behdavar.backservices.common.model.BaseModel;

import java.util.Date;

public class CustomerModel extends BaseModel<String> {

    private CustomerType customerType;
    private String shahabCode;
    private String corporateType;
    private String firstName;
    private String lastName;
    private String englishFirstName;
    private String englishLastName;
    private String foreignName;
    private String foreignerNo;
    private String fullName;
    private String englishFullName;
    private String fatherName;
    private Date birthDate;
    private String birthPlace;
    private String birthPlaceTitle;
    private String issuePlace;
    private String issuePlaceTitle;
    private Date foundationDate;
    private String foundationPlace;
    private String foundationPlaceTitle;
    private String cityCode;
    private String registrationCode;
    private Date registrationDate;
    private String economicCode;
    private String occupation;    //economic section
    private Integer employeeCount;
    private String tradeCardId;
    private Date tradeCardExpireDate;
    private String defaultAddress;
    private String defaultEmail;
    private String code;
    private Boolean enable;
    private String nationalityCode;
    private String nationalityTitle;
    private String identityNumber;
    private Gender gender;
    private String genderTitle;
    private String title;

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getShahabCode() {
        return shahabCode;
    }

    public void setShahabCode(String shahabCode) {
        this.shahabCode = shahabCode;
    }

    public String getCorporateType() {
        return corporateType;
    }

    public void setCorporateType(String corporateType) {
        this.corporateType = corporateType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEnglishFirstName() {
        return englishFirstName;
    }

    public void setEnglishFirstName(String englishFirstName) {
        this.englishFirstName = englishFirstName;
    }

    public String getEnglishLastName() {
        return englishLastName;
    }

    public void setEnglishLastName(String englishLastName) {
        this.englishLastName = englishLastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEnglishFullName() {
        return englishFullName;
    }

    public void setEnglishFullName(String englishFullName) {
        this.englishFullName = englishFullName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthPlaceTitle() {
        return birthPlaceTitle;
    }

    public void setBirthPlaceTitle(String birthPlaceTitle) {
        this.birthPlaceTitle = birthPlaceTitle;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getIssuePlaceTitle() {
        return issuePlaceTitle;
    }

    public void setIssuePlaceTitle(String issuePlaceTitle) {
        this.issuePlaceTitle = issuePlaceTitle;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getFoundationPlace() {
        return foundationPlace;
    }

    public void setFoundationPlace(String foundationPlace) {
        this.foundationPlace = foundationPlace;
    }

    public String getFoundationPlaceTitle() {
        return foundationPlaceTitle;
    }

    public void setFoundationPlaceTitle(String foundationPlaceTitle) {
        this.foundationPlaceTitle = foundationPlaceTitle;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEconomicCode() {
        return economicCode;
    }

    public void setEconomicCode(String economicCode) {
        this.economicCode = economicCode;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getTradeCardId() {
        return tradeCardId;
    }

    public void setTradeCardId(String tradeCardId) {
        this.tradeCardId = tradeCardId;
    }

    public Date getTradeCardExpireDate() {
        return tradeCardExpireDate;
    }

    public void setTradeCardExpireDate(Date tradeCardExpireDate) {
        this.tradeCardExpireDate = tradeCardExpireDate;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getDefaultEmail() {
        return defaultEmail;
    }

    public void setDefaultEmail(String defaultEmail) {
        this.defaultEmail = defaultEmail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public String getForeignerNo() {
        return foreignerNo;
    }

    public void setForeignerNo(String foreignerNo) {
        this.foreignerNo = foreignerNo;
    }

    public String getGenderTitle() {
        return genderTitle;
    }

    public void setGenderTitle(String genderTitle) {
        this.genderTitle = genderTitle;
    }

    public String getNationalityTitle() {
        return nationalityTitle;
    }

    public void setNationalityTitle(String nationalityTitle) {
        this.nationalityTitle = nationalityTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
