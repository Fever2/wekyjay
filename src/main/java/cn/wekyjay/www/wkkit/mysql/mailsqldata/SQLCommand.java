package cn.wekyjay.www.wkkit.mysql.mailsqldata;

enum SQLCommand {
	// ������
	CREATE_TABLE(
			"CREATE TABLE IF NOT EXISTS `maildata` (" +
			"`id` INT UNSIGNED AUTO_INCREMENT," +
			"`player` VARCHAR(100) NULL DEFAULT NULL," +
			"`kitname` VARCHAR(100) NULL DEFAULT NULL," +
			"`num` INT NOT NULL," +
			"PRIMARY KEY (`id`)," +
			"UNIQUE KEY (`player`, `kitname`)"+ 
			") DEFAULT CHARSET=utf8 "
	),
	

	// �������
	ADD_DATA(
			"INSERT INTO `maildata` " +
			"(`id`,`player`,`kitname`,`num`)" +
			"VALUES (?, ?, ?, ?)"
	),
	
	// ������ȡ��������
	UPDATE_NUM_DATA(
			"UPDATE `maildata` SET `num` = ? WHERE `player` = ? AND `kitname` = ? "
	),
	
	
	// ɾ������
	DELETE_DATA(
			"DELETE FROM `maildata` WHERE `player` = ? AND `kitname` = ?"
	),
	
	SELECT_DATA(
			"SELECT * FROM `maildata` WHERE `player` = ?"
	);
	
	
	private String command;
	
	SQLCommand(String command){
        this.command = command;
	}
	public String commandToString() {
		return command;
	}
}
