/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trixware.erp.prodezydesktop.Model;

/**
 *
 * @author dell
 */
public class Main_typeModel {
    
    private int totalmaintain ;
    //int tlcostmain ;
    private float tlcostmain ;
    private int main_TypeId ;
    private String type_Str ;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getTlcostmain() {
        return tlcostmain;
    }

    public void setTlcostmain(float tlcostmain) {
        this.tlcostmain = tlcostmain;
    }

    public int getTotalmaintain() {
        return totalmaintain;
    }

    public void setTotalmaintain(int totalmaintain) {
        this.totalmaintain = totalmaintain;
    }

    public int getMain_TypeId() {
        return main_TypeId;
    }

    public void setMain_TypeId(int main_TypeId) {
        this.main_TypeId = main_TypeId;
    }

    public String getType_Str() {
        return type_Str;
    }

    public void setType_Str(String type_Str) {
        this.type_Str = type_Str;
    }
    
}
