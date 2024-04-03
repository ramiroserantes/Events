INSERT INTO Guild (id, maxLvl, kname, lastTimeUpdated) VALUES (1, 0, null, DATE_SUB(CURDATE(), INTERVAL 1 DAY));

INSERT INTO Events (worldScope, name, startDate, endDate) VALUES ('ALL', 'GatheringEvent', DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY));
INSERT INTO Events (worldScope, name, startDate, endDate) VALUES ('ALL', 'HuntingEvent', DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY));
INSERT INTO Events (worldScope, name, startDate, endDate) VALUES ('ALL', 'MiningEvent', DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY));
