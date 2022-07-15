package org.systemsbiology.jrap.grits.stax;

import java.util.ArrayList;
import java.util.List;


/**
 * Class to expose methods for parsing an mzXML or mzML file for general processing in GRITS.
 * 
 * @author D Brent Weatherly (dbrentw@uga.edu)
 *
 */
public class GRITSMSXMLParser {
	MSXMLParser parser = null;

	/**
	 * Instantiate the MXXMLParser.
	 * 
	 * @param sFileName
	 */
	public GRITSMSXMLParser( String sFileName ) {
		parser = new MSXMLParser(sFileName);
	}

	/**
	 * Parse the specified xml file (from constructor) to pull out the MS1 scans
	 * 
	 * @return list of scan numbers
	 */
	public List<Integer> getMS1ScanList() {
		List<Integer> ms1ScanList = new ArrayList<>();
		try {
			if ( parser != null ) {
				for( int i = 1; i < parser.getMaxScanNumber() + 1; i++ ) {
					ScanHeader header = parser.rapHeader(i);
					if ( header != null && header.getMsLevel() == 1 ) {
						ms1ScanList.add(i);
					}
				}
			}	
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		return ms1ScanList;
	}

	/**
	 * Parse the specified xml file (from constructor) to pull out the scan objects for the specified scans
	 * and any subscans.
	 * 
	 * @param iScanNumber
	 * @return list of Scan objects
	 */
	public List<Scan> getScanAndSubScans( int iScanNumber ) {
		List<Scan> scanList = new ArrayList<>();
		try {
			if ( parser != null ) {
				Scan parentScan = parser.rap(iScanNumber);
				if( parentScan != null ) {
					scanList.add(parentScan);
					boolean bGo = true;
					for( int i = iScanNumber + 1; bGo && i < parser.getMaxScanNumber(); i++ ) {
						ScanHeader header = parser.rapHeader(i);
						if( header.getMsLevel() == parentScan.getHeader().msLevel ) {
							bGo = false;
						} else if ( header.getPrecursorScanNum() == iScanNumber ) {
							Scan scan = parser.rap(i);
							scanList.add(scan);
						}
					}
				}
			}
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		return scanList;
	}

	/**
	 * Parse the specified xml file (from constructor) to pull out the scan object for the specified scans
	 * 
	 * @param iScanNumber
	 * @return Scan object
	 */
	public Scan getScan( int iScanNumber ) {
		try {
			if ( parser != null ) {
				return parser.rap(iScanNumber);
			}
		} catch( Exception ex ) {
			ex.printStackTrace();
		}
		return null;
	}
}
