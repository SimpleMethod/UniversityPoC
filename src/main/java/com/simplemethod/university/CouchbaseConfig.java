package com.simplemethod.university;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class CouchbaseConfig {


    private String[] connection;
    private String[] username;
    private String[] password;
    public String[] bucketName;

    public String[] getBucketName() {
        return bucketName;
    }

    public Bucket openConnectionStudent()
    {
        Cluster cluster = Cluster.connect(connection[0], username[0], password[0]);
        return cluster.bucket(bucketName[0]);
    }
    public Cluster openClusterStudent()
    {
        return  Cluster.connect(connection[0], username[0], password[0]);
    }


    public void init(String[] connection, String[] username, String[] password, String[] bucketName) {
        this.connection = connection;
        this.username = username;
        this.password = password;
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "CouchbaseConfig{" +
                "connection=" + Arrays.toString(connection) +
                ", username=" + Arrays.toString(username) +
                ", password=" + Arrays.toString(password) +
                ", bucketName=" + Arrays.toString(bucketName) +
                '}';
    }
}
