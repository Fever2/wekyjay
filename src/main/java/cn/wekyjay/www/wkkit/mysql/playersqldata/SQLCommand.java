package cn.wekyjay.www.wkkit.mysql.playersqldata;

enum SQLCommand {
	// 创建表
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
	
	// 锁表
	LOCK_TABLE(
			"LOCK TABLES player WRITE"
	),

	// 添加数据
	ADD_DATA(
			"INSERT INTO `player` " +
			"(`id`,`player`,`kitname`,`data`,`time`)" +
			"VALUES (?, ?, ?, ?, ?)"
	),
	
	// 更新领取次数数据
	UPDATE_TIME_DATA(
			"UPDATE `player` SET `time` = ? WHERE `player` = ? AND `kitname` = ? "
	),
	
	// 更新领取时间数据
	UPDATE_DATA_DATA(
			"UPDATE `player` SET `data` = ? WHERE `player` = ? AND `kitname` = ? "
	),
	
	// 删除数据
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
