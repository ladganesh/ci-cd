/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class Main_typeModel_quality {
      private int record_id;
      private int id;
     private Float std_mark;
    private String name;
    private float ull,lll;
   private int uom;

    public int getUom() {
        return uom;
    }

    public void setUom(int uom) {
        this.uom = uom;
    }

   

   public Main_typeModel_quality()
    {
    
    } 

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }
    public Main_typeModel_quality(int id,String name,Float std_mark,float lll,float  ull)
    {
        this.id=id;
        this.name=name;
        this.std_mark=std_mark;
        this.ull=ull;
        this.lll=lll;
    }
    public Float getStd_mark() 
    {
        return std_mark;
    }

    public void setStd_mark(Float std_mark) {
        this.std_mark = std_mark;
    }
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUll() {
        return ull;
    }

    public void setUll(float ull) {
        this.ull = ull;
    }

    public float getLll() {
        return lll;
    }

    public void setLll(float lll) {
        this.lll = lll;
    }
    


  
}
