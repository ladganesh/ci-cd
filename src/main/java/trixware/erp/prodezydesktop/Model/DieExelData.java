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
public class DieExelData {
    
    private String die_code;
    private String maint_dt;
    private String emp_nm;
    private String stroke_mt;
    private String activity;
    private String act_cost;
    private String total_cost;
    private String die_purchase_cost;

    public DieExelData(String die_code,String die_purchase_cost, String maint_dt, String emp_nm, String stroke_mt, String activity, String act_cost, String total_cost) {
        this.die_code = die_code;
        this.maint_dt = maint_dt;
        this.emp_nm = emp_nm;
        this.stroke_mt = stroke_mt;
        this.activity = activity;
        this.act_cost = act_cost;
        this.total_cost = total_cost;
        this.die_purchase_cost=die_purchase_cost;
    }

    public String getDie_purchase_cost() {
        return die_purchase_cost;
    }

    public void setDie_purchase_cost(String die_purchase_cost) {
        this.die_purchase_cost = die_purchase_cost;
    }
    
    public String getDie_code() {
        return die_code;
    }

    public void setDie_code(String die_code) {
        this.die_code = die_code;
    }

    public String getMaint_dt() {
        return maint_dt;
    }

    public void setMaint_dt(String maint_dt) {
        this.maint_dt = maint_dt;
    }

    public String getEmp_nm() {
        return emp_nm;
    }

    public void setEmp_nm(String emp_nm) {
        this.emp_nm = emp_nm;
    }

    public String getStroke_mt() {
        return stroke_mt;
    }

    public void setStroke_mt(String stroke_mt) {
        this.stroke_mt = stroke_mt;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAct_cost() {
        return act_cost;
    }

    public void setAct_cost(String act_cost) {
        this.act_cost = act_cost;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }
   
}
