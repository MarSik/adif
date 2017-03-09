package org.marsik.ham.adif;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

import org.gavaghan.geodesy.GlobalCoordinates;
import org.marsik.ham.adif.enums.AntPath;
import org.marsik.ham.adif.enums.Band;
import org.marsik.ham.adif.enums.Continent;
import org.marsik.ham.adif.enums.Mode;
import org.marsik.ham.adif.enums.Propagation;
import org.marsik.ham.adif.enums.QslRcvd;
import org.marsik.ham.adif.enums.QslSent;
import org.marsik.ham.adif.enums.QslVia;
import org.marsik.ham.adif.enums.QsoComplete;
import org.marsik.ham.adif.enums.QsoUploadStatus;
import org.marsik.ham.adif.types.Iota;
import org.marsik.ham.adif.types.Sota;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Adif3Record {
    /**
     * the contacted station's complete mailing address: full name, street address, city, postal code, and country
     * ASCII, Multiline
     */
    private String address;

    /**
     * the contacted station's complete mailing address: full name, street address, city, postal code, and country
     * UTF-8, Multiline
     */
    private String addressIntl;

    /**
     * the contacted station's operator's age in years
     */
    private Integer age;

    /**
     * the geomagnetic A index at the time of the QSO
     */
    private Double aIndex;

    /**
     * the logging station's antenna azimuth, in degrees
     */
    private Double antAz;

    /**
     * the logging station's antenna elevation, in degrees
     */
    private Double antEl;

    /**
     * the signal path
     */
    private AntPath antPath;

    /**
     * the contacted station's ARRL section
     * TODO enum
     */
    private String arrlSect;

    /**
     * the list of awards submitted to a sponsor.
     *
     * note that this field might not be used in a QSO record.  It might be used to convey information about a user’s
     * “Award Account” between an award sponsor and the user.  For example, AA6YQ might submit a request for DXCC
     * awards by sending the following:
     *
     * <CALL:5>AA6YQ
     * <AWARD_SUBMITTED:64>FISTS_CENTURY_BASIC,FISTS_CENTURY_SILVER,FISTS_SPECTRUM_100-160m
     * TODO enum
     */
    private List<String> awardSubmitted;

    /**
     * the list of awards granted by a sponsor.
     *
     * note that this field might not be used in a QSO record.  It might be used to convey information about a user’s
     * “Award Account” between an award sponsor and the user.  For example, in response to a request “send me a list
     * of the DXCC awards granted to AA6YQ”, this might be received:
     *
     * <CALL:5>AA6YQ
     * <AWARD_GRANTED:64>FISTS_CENTURY_BASIC,FISTS_CENTURY_SILVER,FISTS_SPECTRUM_100-160m
     * TODO enum
     */
    private List<String> awardGranted;

    /**
     * QSO Band
     */
    private Band band;

    /**
     * in a split frequency QSO, the logging station's receiving band
     */
    private Band bandRx;

    /**
     * the contacted station's Callsign
     */
    private String call;

    /**
     * contest check (e.g. for ARRL Sweepstakes)
     */
    private String check;

    /**
     * contest class (e.g. for ARRL Field Day)
     * TODO the field is called class
     */
    private String contestClass;

    /**
     * the date the QSO was last uploaded to the Club Log online service (UTC)
     */
    private ZonedDateTime clublogQsoUploadDate;

    /**
     * the upload status of the QSO on the Club Log online service
     */
    private QsoUploadStatus clublogQsoUploadStatus;

    /**
     * the contacted station's Secondary Administrative Subdivision of contacted station (e.g. US county, JA Gun),
     * in the specified format
     * TODO enum
     */
    private String cnty;

    /**
     * comment field for QSO (ASCII)
     */
    private String comment;

    /**
     * comment field for QSO
     */
    private String commentIntl;

    /**
     * the contacted station's Continent
     */
    private Continent cont;

    /**
     * the callsign of the individual operating the contacted station
     */
    private String contactedOp;

    /**
     * QSO Contest Identifier
     * use enumeration values for interoperability
     */
    private String contestId;

    /**
     * the contacted station's DXCC entity name
     */
    private String country;

    /**
     * the contacted station's DXCC entity name
     */
    private String countryIntl;

    /**
     * the contacted station's CQ Zone
     */
    private Integer cqz;

    /**
     * the list of credits sought for this QSO
     *
     * Use of data type AwardList and enumeration Award are deprecated
     * todo enum Credit
     */
    private List<String> creditSubmitted;

    /**
     * the list of credits granted to this QSO
     *
     * Use of data type AwardList and enumeration Award are deprecated
     * todo enum credit
     */
    private List<String> creditGranted;

    /**
     * the distance between the logging station and the contacted station in kilometers via the specified signal path
     */
    private Double distance;

    /**
     * the contacted station's Country Code
     */
    private Integer dxcc;

    /**
     * the contacted station's email address
     */
    private String email;

    /**
     * the contacted station's owner's callsign
     */
    private String eqCall;

    /**
     * date QSL received from eQSL.cc
     * (only valid if EQSL_QSL_RCVD is Y, I, or V)
     * (V deprecated)
     */
    private ZonedDateTime eqslQslRDate;

    /**
     * date QSL sent to eQSL.cc
     * (only valid if EQSL_QSL_SENT is Y, Q, or I)
     */
    private ZonedDateTime eqslQslSDate;

    /**
     * eQSL.cc QSL received status
     *
     * instead of V (deprecated) use
     * <CREDIT_GRANTED:39>DXCC:eqsl,DXCC_BAND:eqsl,DXCC_MODE:eqsl
     *
     * Default Value: N
     */
    private QslRcvd eqslQslRcvd;

    /**
     * eQSL.cc QSL sent status
     *
     * Default Value: N
     */
    private QslSent eqslQslSent;

    /**
     * the contacted station's FISTS CW Club member information, which starts with a sequence of Digits
     * giving the member's number.  For upward-compatibility, any characters after the last digit of the member
     * number sequence must be allowed by applications.
     */
    private String fists;

    /**
     * the contacted station's FISTS CW Club Century Certificate (CC) number, which is a sequence of Digits only
     * (no sign and no decimal point)
     */
    private String fistsCc;

    /**
     * new EME "initial"
     */
    private Boolean forceInt;

    /**
     * QSO frequency in Megahertz
     */
    private Double freq;

    /**
     * in a split frequency QSO, the logging station's receiving frequency in Megahertz
     */
    private Double freqRx;

    /**
     * the contacted station's 2-character, 4-character, 6-character, or 8-character Maidenhead Grid Square
     * todo maidenhead/coordinate type
     */
    private String gridsquare;

    // todo guest_op alias for operator

    /**
     * the date the QSO was last uploaded to the HRDLog.net online service
     */
    private ZonedDateTime hrdlogQsoUploadDate;

    /**
     * the upload status of the QSO on the HRDLog.net online service
     */
    private QsoUploadStatus hrdlogQsoUploadStatus;

    /**
     * the contacted station's IOTA designator, in format CC-XXX, where
     * CC is a member of the Continent enumeration
     * XXX is the island designator, where 0 <= XXX ,<= 999 [use leading zeroes]
     */
    private Iota iota;

    /**
     * the contacted station's IOTA Island Identifier
     */
    private String iotaIslandId;

    /**
     * the contacted station's ITU zone
     */
    private Integer ituz;

    /**
     * the geomagnetic K index at the time of the QSO
     */
    private Double kIndex;

    /**
     * the contacted station's cooridinates
     */
    private GlobalCoordinates coordinates;

    /**
     * date QSL received from ARRL Logbook of the World
     * (only valid if LOTW_QSL_RCVD is Y, I, or V)
     * (V deprecated)
     */
    private ZonedDateTime lotwQslRDate;

    /**
     * date QSL sent to ARRL Logbook of the World
     * (only valid if LOTW_QSL_SENT is Y, Q, or I)
     */
    private ZonedDateTime lotwQslSDate;

    /**
     * ARRL Logbook of the World QSL received status
     *
     *  instead of V (deprecated) use
     *  <CREDIT_GRANTED:39>DXCC:lotw,DXCC_BAND:lotw,DXCC_MODE:lotw
     *
     *  Default Value: N
     */
    private QslRcvd lotwQslRcvd;

    /**
     * ARRL Logbook of the World QSL sent status
     *
     * Default Value: N
     */
    private QslSent lotwQslSent;

    /**
     * maximum length of meteor scatter bursts heard by the logging station, in seconds
     */
    private Integer maxBursts;

    /**
     * QSO Mode
     */
    private Mode mode;

    /**
     * For Meteor Scatter QSOs, the name of the meteor shower in progress
     */
    private String msShower;

    /**
     * the logging station's city
     */
    private String myCity;

    /**
     * the logging station's city
     */
    private String myCityIntl;

    /**
     * the logging station's Secondary Administrative Subdivision  (e.g. US county, JA Gun) , in the specified format
     */
    private String myCnty;

    /**
     * the logging station's DXCC entity name
     */
    private String myCountry;
    private String myCountryIntl;

    /**
     * the logging station's CQ Zone
     */
    private Integer myCqZone;

    /**
     * the logging station's Country Code
     */
    private Integer myDxcc;

    /**
     * the logging station's FISTS CW Club member information, which starts with a sequence of Digits giving
     * the member's number. For upward-compatibility, any characters after the last digit of the member number
     * sequence must be allowed by applications.
     */
    private String myFists;

    /**
     * the logging station's 2-character, 4-character, 6-character, or 8-character Maidenhead Grid Square
     */
    private String myGridSquare;

    /**
     * the logging station's IOTA designator, in format CC-XXX, where
     * CC is a member of the Continent enumeration
     * XXX is the island designator, where 0 <= XXX ,<= 999  [use leading zeroes]
     */
    private Iota myIota;

    /**
     * the logging station's IOTA Island Identifier
     */
    private String myIotaIslandId;

    /**
     * the logging station's ITU zone
     */
    private Integer myItuZone;

    /**
     * the logging station's latitude
     */
    private GlobalCoordinates myCoordinates;

    /**
     * the logging operator's name
     */
    private String myName;
    private String myNameIntl;

    /**
     * the logging station's postal code
     */
    private String myPostalCode;
    private String getMyPostalCodeIntl;

    /**
     * description of the logging station's equipment
     */
    private String myRig;
    private String myRigIntl;

    /**
     * special interest activity or event
     */
    private String mySig;
    private String mySigIntl;

    /**
     * special interest activity or event information
     */
    private String mySigInfo;
    private String mySigInfoIntl;

    /**
     * the logging station's International SOTA Reference.
     */
    private Sota mySotaRef;

    /**
     * the code for the logging station's Primary Administrative Subdivision (e.g. US State, JA Island, VE Province)
     */
    private String myState;

    /**
     * the logging station's street
     */
    private String myStreet;
    private String myStreetIntl;

    /**
     * two US counties in the case where the logging station is located on a border between two counties,
     * representing counties that the contacted station may claim for the CQ Magazine USA-CA award program.
     *
     * E.g.
     * MA,Franklin:MA,Hampshire
     */
    private List<String> myUsaCaCounties;

    /**
     * two or four adjacent Maidenhead grid locators, each four characters long, representing the logging
     * station's grid squares that the contacted station may claim for the ARRL VUCC award program.
     *
     * E.g.
     * EN98,FM08,EM97,FM07
     * todo maidenhead
     */
    private List<String> myVuccGrids;

    /**
     * the contacted station's operator's name
     */
    private String name;
    private String nameIntl;

    /**
     * QSO notes
     */
    private String notes;
    private String notesIntl;

    /**
     * the number of meteor scatter bursts heard by the logging station
     */
    private Integer nrBursts;

    /**
     * the number of meteor scatter pings heard by the logging station
     */
    private Integer nrPings;

    /**
     * the logging operator's callsign
     * if STATION_CALLSIGN is absent, OPERATOR shall be treated as both the logging station's callsign
     * and the logging operator's callsign
     */
    private String operator;

    /**
     * the callsign of the owner of the station used to log the contact (the callsign of the OPERATOR's host)
     * if OWNER_CALLSIGN is absent, STATION_CALLSIGN shall be treated as both the logging station's callsign
     * and the callsign of the owner of the station
     */
    private String ownerCallsign;

    /**
     * the contacted station's WPX prefix
     */
    private String pfx;

    /**
     * contest precedence (e.g. for ARRL Sweepstakes)
     */
    private String precedence;

    /**
     * QSO propagation mode
     */
    private Propagation propMode;

    /**
     * public encryption key
     */
    private String publicKey;

    /**
     * the date the QSO was last uploaded to the QRZ.COM online service
     */
    private ZonedDateTime qrzcomQsoUploadDate;

    /**
     * the upload status of the QSO on the QRZ.COM online service
     */
    private QsoUploadStatus qrzcomQsoUploadStatus;

    /**
     * QSL card message
     */
    private String qslMsg;
    private String qslMsgIntl;

    /**
     * QSL received date
     * (only valid if QSL_RCVD is Y, I, or V)
     * (V deprecated)
     */
    private LocalDate qslRDate;

    /**
     * QSL sent date
     * (only valid if QSL_SENT is Y, Q, or I)
     */
    private LocalDate qslSDate;

    /**
     * QSL received status
     *
     * instead of V (deprecated) use
     * <CREDIT_GRANTED:39>DXCC:card,DXCC_BAND:card,DXCC_MODE:card
     *
     * Default Value: N
     */
    private QslRcvd qslRcvd;

    /**
     * means by which the QSL was received by the logging station
     * use of M (manager) is deprecated
     */
    private QslVia qslRcvdVia;

    /**
     * QSL sent status
     *
     * Default Value: N
     */
    private QslSent qslSent;

    /**
     * means by which the QSL was sent by the logging station
     *
     * use of M (manager) is deprecated
     */
    private QslVia qslSentVia;

    /**
     * the contacted station's QSL route
     */
    private String qslVia;

    /**
     * indicates whether the QSO was complete from the perspective of the logging station
     */
    private QsoComplete qsoComplete;

    /**
     * date on which the QSO started in UTC
     */
    private LocalDate qsoDate;

    /**
     * date on which the QSO ended in UTC
     */
    private LocalDate qsoDateOff;

    /**
     * indicates whether the QSO was random or scheduled
     */
    private Boolean qsoRandom;

    /**
     * the contacted station's city
     */
    private String qth;
    private String qthIntl;

    /**
     * description of the contacted station's equipment
     */
    private String rig;
    private String rigIntl;

    /**
     * signal report from the contacted station
     */
    private String rstRcvd;

    /**
     * signal report sent to the contacted station
     */
    private String rstSent;

    /**
     * the contacted station's transmitter power in watts
     */
    private Double rxPwr;

    /**
     * satellite mode
     */
    private String satMode;

    /**
     * name of satellite
     */
    private String satName;

    /**
     * the solar flux at the time of the QSO
     */
    private Double sfi;

    /**
     * the name of the contacted station's special activity or interest group
     */
    private String sig;
    private String sigIntl;

    /**
     * information associated with the contacted station's activity or interest group
     */
    private String sigInfo;
    private String sigInfoIntl;

    /*
     * True indicates that the contacted station's operator is now a Silent Key.
     */
    private Boolean silentKey;

    /**
     * the contacted station's Straight Key Century Club (SKCC) member information
     */
    private String skcc;

    /**
     * the contacted station's International SOTA Reference.
     */
    private Sota sotaRef;

    /**
     * contest QSO received serial number
     */
    private Integer srx;

    /**
     * contest QSO received information
     * use Cabrillo format to convey contest information for which ADIF fields are not specified
     * in the event of a conflict between information in a dedicated contest field and this field,
     * information in the dedicated contest field shall prevail
     */
    private String srxString;

    /**
     * the code for the contacted station's Primary Administrative Subdivision (e.g. US State, JA Island, VE Province)
     */
    private String state;

    /**
     * the logging station's callsign (the callsign used over the air)
     * if STATION_CALLSIGN is absent, OPERATOR shall be treated as both the logging station's callsign
     * and the logging operator's callsign
     */
    private String stationCallsign;

    /**
     * contest QSO transmitted serial number
     */
    private Integer stx;

    /**
     * contest QSO transmitted information
     * use Cabrillo format to convey contest information for which ADIF fields are not specified
     * in the event of a conflict between information in a dedicated contest field and this field,
     * information in the dedicated contest field shall prevail
     */
    private String stxString;

    /**
     * QSO Submode
     *
     * use enumeration values for interoperability
     */
    private String submode;

    /**
     * indicates that the QSO information pertains to an SWL report
     */
    private Boolean swl;

    /**
     * Ten-Ten number
     */
    private Integer tenTen;

    /**
     * HHMM or HHMMSS in UTC
     * in the absence of <QSO_DATE_OFF>, the QSO duration is less than 24 hours
     */
    private LocalTime timeOff;

    /**
     * HHMM or HHMMSS in UTC
     */
    private LocalTime timeOn;

    /**
     * the logging station's power in watts
     */
    private Double txPwr;

    /*
     * the contacted station's UKSMG member number
     */
    private Integer uksmg;

    /**
     * two US counties in the case where the contacted station is located on a border between two counties,
     * representing counties credited to the QSO for the CQ Magazine USA-CA award program.
     *
     * E.g.
     * MA,Franklin:MA,Hampshire
     */
    private List<String> usaCaCounties;

    // veProv - alias to state

    /**
     * two or four adjacent Maidenhead grid locators, each four characters long, representing the contacted station's
     * grid squares credited to the QSO for the ARRL VUCC award program.
     *
     * E.g.
     * EN98,FM08,EM97,FM07
     * todo maidenhead
     */
    private List<String> vuccGrids;

    /**
     * the contacted station's URL
     */
    private String web;

    // todo app specific fields, user defined fields
}
