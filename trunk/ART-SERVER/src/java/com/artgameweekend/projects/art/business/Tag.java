/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artgameweekend.projects.art.business;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Id;

/**
 *
 * @author pierre
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Tag implements Serializable
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Id
    private Key idKey;

    @Persistent
    private String name;

    @Persistent
    private String blobKey;

    @Persistent
    private double lat;

    @Persistent
    private double lon;

    @Persistent
    private Blob image;


    public Tag() { }
    public Tag( String name, String blobKey , double lat , double lon )
    {
        this.name = name; 
        this.blobKey = blobKey;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getId()
    {
        return idKey.getId();
    }

 
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Key getKey()
    {
        return idKey;
    }

    public void setKey(Key key)
    {
        this.idKey = key;
    }

        public void setImage(byte[] bytes) {
        this.image = new Blob(bytes);
    }

    public byte[] getImage()
    {
        return this.image.getBytes();
    }

    public double getLat()
    {
        return this.lat;
    }

    public void setLat( double lat )
    {
        this.lat = lat;
    }

    public double getLon()
    {
        return this.lon;
    }

    public void setLon( double lon )
    {
        this.lon = lon;
    }

}
