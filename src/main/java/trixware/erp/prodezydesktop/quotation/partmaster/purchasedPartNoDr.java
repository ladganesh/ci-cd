/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quotation.partmaster;

import java.util.List;

/**
 *
 * @author Akshay
 */
public class purchasedPartNoDr {
    private String partType;

    private List<String[]> lst;

    public String getPartType() {
        return partType;
    }

    public List<String[]> getLst() {
        return lst;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public void setLst(List<String[]> lst) {
        this.lst = lst;
    }

}

//public class purchasedDR
//{
//}