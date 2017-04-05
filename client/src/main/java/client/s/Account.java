package client.s;

public class Account {
	private String bankCode;
	private String bankKanjiName;
	private String bankKanaName;
	private String branchCode;
	private String branchKanjiName;
	private String branchKanaName;
	private String typeCode;
	private String typeName;
	private String accountNo;
	private String torkNo;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankKanjiName() {
		return bankKanjiName;
	}

	public void setBankKanjiName(String bankKanjiName) {
		this.bankKanjiName = bankKanjiName;
	}

	public String getBankKanaName() {
		return bankKanaName;
	}

	public void setBankKanaName(String bankKanaName) {
		this.bankKanaName = bankKanaName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchKanjiName() {
		return branchKanjiName;
	}

	public void setBranchKanjiName(String branchKanjiName) {
		this.branchKanjiName = branchKanjiName;
	}

	public String getBranchKanaName() {
		return branchKanaName;
	}

	public void setBranchKanaName(String branchKanaName) {
		this.branchKanaName = branchKanaName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getTorkNo() {
		return torkNo;
	}

	public void setTorkNo(String torkNo) {
		this.torkNo = torkNo;
	}

	@Override
	public String toString() {
		return "Account [bankCode=" + bankCode + ", bankKanjiName=" + bankKanjiName + ", bankKanaName=" + bankKanaName
				+ ", branchCode=" + branchCode + ", branchKanjiName=" + branchKanjiName + ", branchKanaName="
				+ branchKanaName + ", typeCode=" + typeCode + ", typeName=" + typeName + ", accountNo=" + accountNo
				+ ", torkNo=" + torkNo + "]";
	}

}
