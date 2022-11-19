package dio.quebec.parking.sevice;

import dio.quebec.parking.controller.dto.ParkingDTO;
import dio.quebec.parking.exception.ParkingNotFoundException;
import dio.quebec.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap<>();

    public List<Parking> findAll() {
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Parking findById(String id) {
        Parking parking = parkingMap.get(id);
        if (parking == null) {
            throw new ParkingNotFoundException(id);
        }
        return parkingMap.get(id);
    }

    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(uuid, parkingCreate);
        return parkingCreate;
    }

    public void delete(String id) {
        findById(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parkingMap.replace(id, parking);
        return parking;
    }

    public Parking exit(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());

        var exitTime = findById(id).getExitDate();
        var entryTime = findById(id).getEntryDate();

        Duration lenghtOfStay = Duration.between(entryTime, exitTime);

        Long billParking = lenghtOfStay.toSeconds();
        if (billParking < 1200) {
            parking.setBill(0.0);
        } else if (billParking >= 1201 && billParking <= 14400) {
            parking.setBill(15.00);
        } else if (billParking >= 14401 && billParking <= 18000) {
            parking.setBill(20.00);
        } else {
            parking.setBill(50.00);
        }

        return parking;
    }
}
