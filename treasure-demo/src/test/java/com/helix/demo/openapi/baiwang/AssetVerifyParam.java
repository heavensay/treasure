package com.helix.demo.openapi.baiwang;

/**
 * @author ljy
 * @date 2019/7/16 14:17
 */
public class AssetVerifyParam {

    private String orgNo;//所属机构编号
    private String invoiceCode;//发票代码
    private String invoiceNumber;//发票号码
    private String billingDate;//开票日期
    private String checkCode;//校验码后六位，（普票、电子发票时必填）
    private String totalAmount;//合计金额（不含税金额，专票时必填。）

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
