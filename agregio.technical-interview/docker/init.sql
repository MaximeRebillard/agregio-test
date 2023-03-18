CREATE table Park (
    id UUID PRIMARY KEY NOT NULL,
    type varchar(255) NOT NULL
);


CREATE TABLE Capacity (
                       id UUID PRIMARY KEY NOT NULL,
                       energyAmount DOUBLE PRECISION,
                       duration INT,
                       capacity VARCHAR(255) NOT NULL,
                       parkId UUID,
                       FOREIGN KEY (parkId) REFERENCES Park(id)
);

CREATE TABLE Offer (
                       id UUID PRIMARY KEY NOT NULL,
                       creationDate varchar(255),
                       market varchar(255),
                       status VARCHAR(255) NOT NULL,
                       capacityId UUID,
                       FOREIGN KEY (capacityId) references Capacity(id)
);


CREATE TABLE Block (
                       id UUID PRIMARY KEY NOT NULL,
                       timeAmount INT,
                       energyAmount INT,
                       capacity VARCHAR(255) NOT NULL,
                       capacityId UUID,
                       offerId UUID,
                       FOREIGN KEY (capacityId) references Capacity(id),
                       FOREIGN KEY (offerId) references Offer(id)
);


