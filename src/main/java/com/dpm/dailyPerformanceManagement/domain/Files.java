package com.dpm.dailyPerformanceManagement.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileId;
    private String fileName;
    private String fileType;
    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] data;
    private String fileDownloadUri;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kpi_owner_id", referencedColumnName = "id")
    private KpiOwner kpiOwner;

    public Files(String fileId, String fileName, String fileType, byte[] data, String fileDownloadUri) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.fileDownloadUri = fileDownloadUri;
    }
}
