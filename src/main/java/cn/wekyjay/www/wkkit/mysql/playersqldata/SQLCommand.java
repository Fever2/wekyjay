package cn.wekyjay.www.wkkit.mysql.playersqldata;

enum SQLCommand {
	// ������
	CREATE_TABLE(
			"CREATE TABLE IF NOT EXISTS `player` (" +
			"`id` INT UNSIGNED AUTO_INCREMENT," +
			"`player` VARCHAR(100) NULL DEFAULT NULL," +
			"`kitname` VARCHAR(100) NULL DEFAULT NULL," +
			"`data` VARCHAR(100) NULL DEFAULT NULL," +
			"`time` INT NOT NULL," +
			"PRIMARY KEY (`id`)," +
			"UNIQUE KEY (`player`, `kitname`)"+ 
			") DEFAULT CHARSET=utf8 "
	),
	

	// �������
	ADD_DATA(
			"INSERT INTO `player` " +
			"(`id`,`player`,`kitname`,`data`,`time`)" +
			"VALUES (?, ?, ?, ?, ?)"
	),
	
	// ������ȡ��������
	UPDATE_TIME_DATA(
			"UPDATE `player` SET `time` = ? WHERE `player` = ? AND `kitname` = ? "
	),
	
	// ������ȡʱ������
	UPDATE_DATA_DATA(
			"UPDATE `player` SET `data` = ? WHERE `player` = ? AND `kitname` = ? "
	),
	
	// ɾ������
	DELETE_DATA(
			"DELETE FROM `player` WHERE `player` = ? AND `kitname` = ?"
	),
	
	SELECT_DATA(
			"SELECT * FROM `player` WHERE `player` = ?"
	);
	
	
	private String command;
	
	SQLCommand(String command){
        this.command = command;
	}
	public String commandToString() {
		return command;
	}
}
