package com.awssample.costdatalake;

import java.util.ArrayList;
import java.util.List;

public class TAChecks {
    private String id;
    private String description;
    private int costMetaDataPosition;
    private int typeMetaDataPosition;
    private int regionMetaDataPosition;
    private int resourceidMetaDataPosition;














    private ArrayList avalableChecks ;


    public TAChecks(String id, String description,int costMetaDataPosition,int typeMetaDataPosition,int regionMetaDataPosition, int resourceidMetaDataPosition ) {
        this.id=id;
        this.description=description;
        this.costMetaDataPosition=  costMetaDataPosition;
        this.typeMetaDataPosition = typeMetaDataPosition;
        this.resourceidMetaDataPosition=resourceidMetaDataPosition;
        this.regionMetaDataPosition = regionMetaDataPosition;

    }

    public TAChecks() {
        this.avalableChecks = new ArrayList();
        this.avalableChecks.add(new TAChecks("Qch7DwouX1","Low Utilization Amazon EC2 Instances",4,3,0,1));
        this.avalableChecks.add(new TAChecks("hjLMh88uM8","Idle Load Balancers",3,2,0,1));
        this.avalableChecks.add(new TAChecks("DAvU99Dc4C","Underutilized Amazon EBS Volumes",5,3,0,1));
        this.avalableChecks.add(new TAChecks("Ti39halfu8","Amazon RDS Idle DB Instances",6,3,0,1));
        this.avalableChecks.add(new TAChecks("G31sQ1E9U","Underutilized Amazon Redshift Clusters",5,3,1,2));

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<TAChecks> getAvailableChecks()
    {
        return this.avalableChecks;
    }

    public int getTypeMetaDataPosition() { return typeMetaDataPosition; }

    public void setTypeMetaDataPosition(int typeMetaDataPosition) { this.typeMetaDataPosition = typeMetaDataPosition; }

    public int getRegionMetaDataPosition() { return regionMetaDataPosition; }

    public void setRegionMetaDataPosition(int regionMetaDataPosition) { this.regionMetaDataPosition = regionMetaDataPosition; }

    public int getResourceidMetaDataPosition() { return resourceidMetaDataPosition; }

    public void setResourceidMetaDataPosition(int resourceidMetaDataPosition) { this.resourceidMetaDataPosition = resourceidMetaDataPosition; }

    public void setCostMetaDataPosition(int costMetaDataPosition) {
        this.costMetaDataPosition = costMetaDataPosition;
    }

    public int getCostMetaDataPosition() {
        return costMetaDataPosition;
    }

}
