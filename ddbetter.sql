connect 'jdbc:derby:DerbyDBetter;create=true';
maximumdisplaywidth 25;
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.language.logStatementText', 'true');
VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('derby.language.logStatementText');

drop table Business;
drop table Status;
drop table State;

create table State(state_id integer, BN_STATE_OF_REG varchar(15), primary key (state_id));
create table Status(status_id integer, BN_STATUS varchar(15), primary key (status_id));
create table Business(id integer, BN_NAME varchar(200), status_id integer references Status(status_id),
                   BN_REG_DT varchar(15), BN_CANCEL_DT varchar(15), BN_RENEW_DT varchar(15),
                   BN_STATE_NUM varchar(15), state_id integer references State(state_id), BN_ABN varchar(15), primary key (id));

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_BULK(null, 'STATE', 'States.csv', '	', null, null, 0, 0);
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_BULK(null, 'STATUS', 'Status.csv', '	', null, null, 0, 0);
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_BULK(null, 'BUSINESS', 'a.csv', '	', null, null, 0, 0);

select * from Business where upper(BN_NAME) like upper('%warby%');
select * from Status;
select * from State;
