package adminbasic.service;

import adminbasic.entity.*;
import edu.fudan.common.entity.Config;
import edu.fudan.common.entity.Contacts;
import edu.fudan.common.entity.Station;
import edu.fudan.common.entity.TrainType;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;


/**
 * @author fdse
 */
public interface AdminBasicInfoService {

    /**
     * get all contacts
     *
     * @param headers headers
     * @return Response
     */
    Response getAllContacts(  HttpHeaders headers);

    /**
     * add contact with contact information
     *
     * @param c contact information
     * @param headers headers
     * @return Response
     */
    Response addContact(Contacts c, HttpHeaders headers);

    /**
     * delete contact with contact id
     *
     * @param contactsId contact id
     * @param headers headers
     * @return Response
     */
    Response deleteContact( String contactsId, HttpHeaders headers);

    /**
     * modify contact with contact information
     *
     * @param mci contact information
     * @param headers headers
     * @return Response
     */
    Response modifyContact(Contacts mci, HttpHeaders headers);



    /**
     * get all stations
     *
     * @param headers headers
     * @return Response
     */
    Response getAllStations(  HttpHeaders headers);

    /**
     * add station with station information
     *
     * @param s station information
     * @param headers headers
     * @return Response
     */
    Response addStation(Station s, HttpHeaders headers);

    /**
     * delete station with station information
     *
     * @param id station id
     * @param headers headers
     * @return Response
     */
    Response deleteStation(String id, HttpHeaders headers);

    /**
     * modify station with station information
     *
     * @param s station information
     * @param headers headers
     * @return Response
     */
    Response modifyStation(Station s, HttpHeaders headers);



    /**
     * get all trains
     *
     * @param headers headers
     * @return Response
     */
    Response getAllTrains(  HttpHeaders headers);

    /**
     * add train with train type
     *
     * @param t train type
     * @param headers headers
     * @return Response
     */
    Response addTrain(TrainType t, HttpHeaders headers);

    /**
     * delete train with id
     *
     * @param id id
     * @param headers headers
     * @return Response
     */
    Response deleteTrain(String id,   HttpHeaders headers);

    /**
     * modify train with train type
     *
     * @param t train type
     * @param headers headers
     * @return Response
     */
    Response modifyTrain(TrainType t, HttpHeaders headers);



    /**
     * get all configs
     *
     * @param headers headers
     * @return Response
     */
    Response getAllConfigs(  HttpHeaders headers);

    /**
     * add config with config info
     *
     * @param c config info
     * @param headers headers
     * @return Response
     */
    Response addConfig(Config c, HttpHeaders headers);

    /**
     * delete config with name
     *
     * @param name name
     * @param headers headers
     * @return Response
     */
    Response deleteConfig(String name, HttpHeaders headers);

    /**
     * modify config with config info
     *
     * @param c config info
     * @param headers headers
     * @return Response
     */
    Response modifyConfig(Config c, HttpHeaders headers);



    /**
     * get all prices
     *
     * @param headers headers
     * @return Response
     */
    Response getAllPrices(  HttpHeaders headers);

    /**
     * add price with price info
     *
     * @param pi price info
     * @param headers headers
     * @return Response
     */
    Response addPrice(PriceInfo pi, HttpHeaders headers);

    /**
     * delete price with price info
     *
     * @param pricesId price config id
     * @param headers headers
     * @return Response
     */
    Response deletePrice(String pricesId, HttpHeaders headers);

    /**
     * modify price with price info
     *
     * @param pi price info
     * @param headers headers
     * @return Response
     */
    Response modifyPrice(PriceInfo pi, HttpHeaders headers);


}
