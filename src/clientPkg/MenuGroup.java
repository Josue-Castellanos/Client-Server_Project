package clientPkg;

public enum MenuGroup {
	SELECT_GROUP,
	NEW_GROUP,
	PUBLIC_GROUPS,
	MY_GROUPS,
	JOIN_GROUP,		//If public group
	LEAVE_GROUP,	//If already a member
	WRITE,
	ADD_MOD,		//Only Lead Mod
	REM_MOD,		//Only Lead Mod
	CHANGE_LEAD,	//Only Lead Mod
	DELETE_GROUP,	//Only Lead Mod
	SEND_INVITE,
	REPORT_USER,
	GROUPS,
	HOME
}
