package com.awssample.costdatalake;

import java.util.ArrayList;
import java.util.List;

public class TAChecks {
    private String id;
    private String description;
    private int costMetaDataPosition;
    private int typeMetaDataPosition;





    public void setCostMetaDataPosition(int costMetaDataPosition) {
        this.costMetaDataPosition = costMetaDataPosition;
    }



    public int getCostMetaDataPosition() {
        return costMetaDataPosition;
    }


    private ArrayList avalableChecks ;


    public TAChecks(String id, String description,int costMetaDataPosition,int typeMetaDataPosition ) {
        this.id=id;
        this.description=description;
        this.costMetaDataPosition=  costMetaDataPosition;
        this.typeMetaDataPosition = typeMetaDataPosition;
    }

    public TAChecks() {
        this.avalableChecks = new ArrayList();
        this.avalableChecks.add(new TAChecks("Qch7DwouX1","Low Utilization Amazon EC2 Instances",4,3));
        this.avalableChecks.add(new TAChecks("hjLMh88uM8","Idle Load Balancers",3,2));
        this.avalableChecks.add(new TAChecks("DAvU99Dc4C","Underutilized Amazon EBS Volumes",5,3));
        this.avalableChecks.add(new TAChecks("Ti39halfu8","Amazon RDS Idle DB Instances",6,3));
        this.avalableChecks.add(new TAChecks("G31sQ1E9U","Underutilized Amazon Redshift Clusters",5,3));

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

}
