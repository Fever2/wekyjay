package cn.wekyjay.www.wkkit.mysql.cdksqldata;

enum SQLCommand {
	// ������
	CREATE_TABLE(
			"CREATE TABLE IF NOT EXISTS `cdk` (" +
			"`id` INT UNSIGNED AUTO_INCREMENT," +
			"`cdk` VARCHAR(100) NULL DEFAULT NULL," +
			"`kits` VARCHAR(100) NULL DEFAULT NULL," +
			"`date` VARCHAR(100) NULL DEFAULT NULL," +
			"`status` VARCHAR(100) NULL DEFAULT NULL," +
			"`mark` VARCHAR(100) NULL DEFAULT NULL," +
			"PRIMARY KEY (`id`),"+ 
			"UNIQUE KEY (`cdk`, `mark`)"+ 
			") DEFAULT CHARSET=utf8" 
	),
	

	// �������
	ADD_DATA(
			"INSERT INTO `cdk` " +
			"(`id`,`cdk`,`kits`,`date`,`status`,`mark`)" +
			"VALUES (?, ?, ?, ?, ?, ?)"
	),
	
	// ������ȡ״̬
	UPDATE_STATUS_DATA(
			"UPDATE `cdk` SET `status` = ? WHERE `cdk` = ? AND `mark` = ? "
	),
	
	// ����Mark
	UPDATE_MARK_DATA(
			"UPDATE `cdk` SET `mark` = ? WHERE `mark` = ? "
	),
	
	// ɾ������
	DELETE_DATA(
			"DELETE FROM `cdk` WHERE `cdk` = ?"
	),
	// ����CDK
	SELECT_MARK(
			"SELECT * FROM `cdk` WHERE `mark` = ?"
	),
	// ����CDK
	SELECT_CDK(
			"SELECT * FROM `cdk` WHERE `cdk` = ?"
	);
	
	
	private String command;
	
	SQLCommand(String command){
        this.command = command;
	}
	public String commandToString() {
		return command;
	}
}
