package cn.wekyjay.www.wkkit.mysql.mailsqldata;

enum SQLCommand {
	// 创建表
	CREATE_TABLE(
			"CREATE TABLE IF NOT EXISTS `maildata` (" +
					"`id` INT UNSIGNED AUTO_INCREMENT," +
					"`player` VARCHAR(50) NOT NULL," +
					"`kitname` VARCHAR(50) NOT NULL," +
					"`num` SMALLINT UNSIGNED NOT NULL," +
					"PRIMARY KEY (`id`)" +
					") DEFAULT CHARSET=utf8 "
	),


	// 添加数据
	ADD_DATA(
			"INSERT INTO `maildata` " +
					"(`id`,`player`,`kitname`,`num`)" +
					"VALUES (?, ?, ?, ?)"
	),

	// 更新领取次数数据
	UPDATE_NUM_DATA(
			"UPDATE `maildata` SET `num` = ? WHERE `player` = ? AND `kitname` = ? "
	),


	// 删除数据
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
