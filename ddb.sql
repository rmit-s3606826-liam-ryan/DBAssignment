connect 'jdbc:derby:DerbyDB;create=true';
-- because some idiat wanted a business name more than 190 chars long
maximumdisplaywidth 25;
-- for logging
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.language.logStatementText', 'true');
VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY('derby.language.logStatementText');

drop table Business;
drop table Status;
drop table State;

create table Business(temp varchar(20), BN_NAME varchar(200), BN_STATUS varchar(15),
                   BN_REG_DT varchar(15), BN_CANCEL_DT varchar(15), BN_RENEW_DT varchar(15),
                   BN_STATE_NUM varchar(15), BN_STATE_OF_REG varchar(15), BN_ABN varchar(15));
create table State(state_id integer not null generated always as identity (start with 1, increment by 1), BN_STATE_OF_REG varchar(15), primary key (state_id));
create table Status(status_id integer not null generated always as identity (start with 1, increment by 1), BN_STATUS varchar(15), primary key (status_id));

-- import data into table
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE_BULK(null, 'BUSINESS', 'BUSINESS_NAMES_201803.csv', '	', null, null, 0, 1);


--get us a primary key, and drop the useless column
alter table app.Business add id integer not null generated always as identity (start with 1, increment by 1);
alter table app.Business add primary key (id);
alter table app.Business drop column temp;

-- get Registered and Deregistered into Status table
insert into Status (BN_STATUS)
select distinct BN_STATUS
from Business;

-- get the status id's into the business table
alter table app.Business add status_id integer;
alter table app.Business add foreign key (status_id) references app.Status(status_id);
update Business set Business.status_id=(select Status.status_id from Status where Business.BN_STATUS = Status.BN_STATUS) where exists (select * from Status where Business.BN_STATUS = Status.BN_STATUS);
alter table app.Business drop column BN_STATUS;

-- get state initials into State table
insert into State (BN_STATE_OF_REG)
select distinct BN_STATE_OF_REG
from Business;

-- get the state id's into the business table
alter table app.Business add state_id integer;
alter table app.Business add foreign key (state_id) references app.State(state_id);
update Business set Business.state_id=(select State.state_id from State where Business.BN_STATE_OF_REG = State.BN_STATE_OF_REG) where exists (select * from State where Business.BN_STATE_OF_REG = State.BN_STATE_OF_REG);
alter table app.Business drop column BN_STATE_OF_REG;

-- simple queries for testing
select * from Business where upper(BN_NAME) like upper('%warby%');
select * from Status;
select * from State;
