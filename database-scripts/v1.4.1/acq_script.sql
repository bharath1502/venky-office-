use ipsidy_acquirer;
SET SQL_SAFE_UPDATES = 0;

/* Changes for PG_CARD_PROGRAM 17-08-2018 */

ALTER TABLE `PG_CARD_PROGRAM` 
ADD COLUMN `ACQ_PM_ID` BIGINT(20) NOT NULL AFTER `ISSUANCE_CARD_PROGRAM_ID`;

/* Changes for multiple card program mapping to a PM 19-08-2018 */
alter table PG_ISO_CARD_PROGRAM_MAPPING add column AMBIGUITY_PM_ID bigint(20);

update PG_CARD_PROGRAM cp 
join PG_PM_CARD_PROGRAM_MAPPING pm_cp on cp.ID=pm_cp.CARD_PROGRAM_ID
join PG_PROGRAM_MANAGER pm on pm.ID=cp.ACQ_PM_ID  
set cp.ACQ_PM_ID= pm_cp.PM_ID where pm_cp.PM_ID is not null and pm.ISSUANCE_PM_ID is not null; 

SET SQL_SAFE_UPDATES = 1;

commit;
