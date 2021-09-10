package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.util.concurrent.TimeUnit;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())))
        {
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }
        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = TimeUnit.HOURS.convert(outHour - inHour,TimeUnit.MILLISECONDS)/60.0;

        if(duration == 0){
            //on a une diff inférieure a 1 heure donc cacul en fraction d'heure avec un double
            duration = TimeUnit.MINUTES.convert(outHour - inHour,TimeUnit.MILLISECONDS)/60.0;
        }

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}