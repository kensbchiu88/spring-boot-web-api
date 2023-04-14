package com.fitfoxconn.npi.dmp.api.entity.ods;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "\"Organize\"", schema = "scada")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"ID\"", nullable = false)
    private int id;
    @Column(name = "\"Sn\"")
    private Integer sn;
    @Column(name = "\"Code\"")
    private Integer code;
    @Column(name = "\"Name\"")
    private String name;
    @Column(name = "\"Alias\"")
    private String alias;
    @Column(name = "\"CostCenter\"")
    private String costCenter;
    @Column(name = "\"Father_id\"")
    private Integer fatherId;
    @Column(name = "\"NotePath\"")
    private String notePath;
    @Column(name = "\"OrType\"")
    private String orType;
    @Column(name = "\"LindData\"")
    private String lindData;
    @Column(name = "\"Remark\"")
    private String remark;
    @Column(name = "\"IsExpanded\"")
    private Boolean isExpanded;
    @Column(name = "\"IsShow\"")
    private Boolean isShow;
    @Column(name = "\"OgLevel\"")
    private Integer ogLevel;
    @Column(name = "\"DepartMentNumber\"")
    private String departMentNumber;
    @Column(name = "\"OAddress\"")
    private String oAddress;
    @Column(name = "\"Createperson\"")
    private String createPerson;
    @Column(name = "\"Createdatetime\"")
    private Timestamp createDateTime;
    @Column(name = "\"Updateperson\"")
    private String updatePerson;
    @Column(name = "\"Updatedatetime\"")
    private Timestamp udateDateTime;
    @Column(name = "\"Qualityowner\"")
    private String qualityOwner;
    @Column(name = "\"QualityownerEmail\"")
    private String qualityOwnerEmail;
    @Column(name = "\"Equipowner\"")
    private String equipOwner;
    @Column(name = "\"EquipownerEmail\"")
    private String equipOwnerEmail;
    @Column(name = "\"EmployeeTotal\"")
    private Integer employeeTotal;
    @Column(name = "\"EquipmentTotal\"")
    private Integer equipmentTotal;
    @Column(name = "\"GaugeTotal\"")
    private Integer gaugeTotal;

}
