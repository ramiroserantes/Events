DROP TABLE IF EXISTS PlayerMedals;
DROP TABLE IF EXISTS PlayerContribution;
DROP TABLE IF EXISTS GuildMedals;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS ObjectiveProgress;
DROP TABLE IF EXISTS MissionAccept;
DROP TABLE IF EXISTS Objective;
DROP TABLE IF EXISTS Missions;
DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Guild;

CREATE TABLE Guild (
    id BIGINT PRIMARY KEY,
    lvl int NOT NULL DEFAULT 0,
    experience DECIMAL(11,2) NOT NULL DEFAULT 0.0,
    maxLvl int NOT NULL DEFAULT 6,
    kName VARCHAR(20) NULL,
    ampMissions int NOT NULL DEFAULT 0,
    ampBasicRewards DECIMAL(4, 2) NOT NULL DEFAULT 0.0,
    lastTimeUpdated DATE NOT NULL ,
    levelUpMod DECIMAL(4, 2) NOT NULL DEFAULT 0.0
);

CREATE TABLE Player (
    playerId VARCHAR(36) PRIMARY KEY,
    maxMissions INT NULL DEFAULT 3,
    ampBasicRewards DECIMAL(4, 2) NULL DEFAULT 0.0,
    guildId BIGINT NOT NULL,
    FOREIGN KEY (guildId) REFERENCES Guild(id)
);

CREATE TABLE Missions (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    guildId BIGINT NULL,
    title VARCHAR(50) NOT NULL,
    grade CHAR(1) NOT NULL,
    expiration DATE NOT NULL,
    maxCompletions INT NULL,
    FOREIGN KEY (guildId) REFERENCES Guild(id) ON DELETE SET NULL
);


CREATE TABLE Objective (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    missionId BIGINT NULL,
    actionType VARCHAR(10) NOT NULL,
    reqAmount INT NOT NULL,
    target VARCHAR(30) NOT NULL,
    levels INT NULL,
    FOREIGN KEY (missionId) REFERENCES Missions(id)
);

CREATE TABLE MissionAccept (
    playerId VARCHAR(36) NOT NULL,
    missionId BIGINT NOT NULL,
    status VARCHAR(15) NOT NULL,
    acceptDate DATE NOT NULL,
    PRIMARY KEY (playerId, missionId),
    FOREIGN KEY (playerId) REFERENCES Player(playerId),
    FOREIGN KEY (missionId) REFERENCES Missions(id)
);

CREATE TABLE ObjectiveProgress (
    playerId VARCHAR(36) NOT NULL,
    objectiveId BIGINT NOT NULL,
    amount INT NOT NULL DEFAULT 0,
    PRIMARY KEY (playerId, objectiveId),
    FOREIGN KEY (playerId) REFERENCES Player(playerId),
    FOREIGN KEY (objectiveId) REFERENCES Objective(id)
);

CREATE TABLE Events (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    worldScope VARCHAR(20) NOT NULL ,
    name VARCHAR(20) NOT NULL ,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    maxMedals INT NOT NULL DEFAULT 200
);

CREATE TABLE GuildMedals (
    eventId BIGINT NOT NULL,
    guildId BIGINT NOT NULL,
    medals INT NOT NULL DEFAULT 0,
    PRIMARY KEY (eventId, guildId),
    FOREIGN KEY (eventId) REFERENCES Events(id),
    FOREIGN KEY (guildId) REFERENCES Guild(id)
);

CREATE TABLE PlayerContribution (
    playerId VARCHAR(36) NOT NULL,
    eventId BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    medals INT NOT NULL DEFAULT 0,
    PRIMARY KEY (playerId, eventId),
    FOREIGN KEY (playerId) REFERENCES Player(playerId),
    FOREIGN KEY (eventId) REFERENCES Events(id)
);

--------------------------------------------------------------------
-- Triggers
--------------------------------------------------------------------
DROP TRIGGER IF EXISTS update_player_guild_id;

CREATE TRIGGER update_player_guild_id
    BEFORE DELETE ON Guild
    FOR EACH ROW
    UPDATE Player
    SET guildId = 1
    WHERE guildId = OLD.id;



