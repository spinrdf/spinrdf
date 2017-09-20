/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 */

package org.spinrdf.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * A singleton managing statistics for SPIN execution.
 * In TopBraid, this singleton is used as a single entry point for various
 * statistics producing engines such as TopSPIN.
 * The results are displayed in the SPIN Statistics view of TBC.
 *
 * The SPINStatisticsManager is off by default, and needs to be activated
 * with <code>setRecording(true);</code>.
 *
 * @author Holger Knublauch
 * @version $Id: $Id
 */
public class SPINStatisticsManager {

	private static SPINStatisticsManager singleton = new SPINStatisticsManager();
	
	/**
	 * Gets the singleton instance of this class.
	 *
	 * @return the SPINStatisticsManager (never null)
	 */
	public static SPINStatisticsManager get() {
		return singleton;
	}
	
	
	private Set<SPINStatisticsListener> listeners = new HashSet<SPINStatisticsListener>();
	
	private boolean recording;
	
	private boolean recordingNativeFunctions;
	
	private boolean recordingSPINFunctions;
	
	private List<SPINStatistics> stats = Collections.synchronizedList(new LinkedList<SPINStatistics>());
	
	
	/**
	 * <p>addListener.</p>
	 *
	 * @param listener a {@link org.spinrdf.statistics.SPINStatisticsListener} object.
	 */
	public void addListener(SPINStatisticsListener listener) {
		listeners.add(listener);
	}
	

	/**
	 * Adds new statistics and notifies any registered listeners.
	 * This should only be called if <code>isRecording()</code> is true
	 * to prevent the unnecessary creation of SPINStatistics objects.
	 *
	 * @param values  the statistics to add
	 */
	public synchronized void add(Iterable<SPINStatistics> values) {
		addSilently(values);
		notifyListeners();
	}


	/**
	 * Adds new statistics without notifying listeners.
	 * This should only be called if <code>isRecording()</code> is true
	 * to prevent the unnecessary creation of SPINStatistics objects.
	 *
	 * @param values  the statistics to add
	 */
	public void addSilently(Iterable<SPINStatistics> values) {
		for(SPINStatistics s : values) {
			stats.add(s);
		}
	}
	
	
	/**
	 * Gets all previously added statistics.
	 *
	 * @return the statistics
	 */
	public synchronized List<SPINStatistics> getStatistics() {
		return stats;
	}
	
	
	/**
	 * <p>isRecording.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isRecording() {
		return recording;
	}
	
	
	/**
	 * <p>isRecordingNativeFunctions.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isRecordingNativeFunctions() {
		return recordingNativeFunctions;
	}
	
	
	/**
	 * <p>isRecordingSPINFunctions.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isRecordingSPINFunctions() {
		return recordingSPINFunctions;
	}
	
	
	/**
	 * <p>removeListener.</p>
	 *
	 * @param listener a {@link org.spinrdf.statistics.SPINStatisticsListener} object.
	 */
	public void removeListener(SPINStatisticsListener listener) {
		listeners.remove(listener);
	}
	
	
	/**
	 * <p>reset.</p>
	 */
	public synchronized void reset() {
		stats.clear();
		notifyListeners();
	}
	
	
	/**
	 * Notifies all registered SPINStatisticsListeners so that they can refresh themselves.
	 */
	public void notifyListeners() {
		for(SPINStatisticsListener listener : new ArrayList<SPINStatisticsListener>(listeners)) {
			listener.statisticsUpdated();
		}
	}
	
	
	/**
	 * <p>Setter for the field <code>recording</code>.</p>
	 *
	 * @param value a boolean.
	 */
	public void setRecording(boolean value) {
		this.recording = value;
	}
	
	
	/**
	 * <p>Setter for the field <code>recordingNativeFunctions</code>.</p>
	 *
	 * @param value a boolean.
	 */
	public void setRecordingNativeFunctions(boolean value) {
		this.recordingNativeFunctions = value;
	}
	
	
	/**
	 * <p>Setter for the field <code>recordingSPINFunctions</code>.</p>
	 *
	 * @param value a boolean.
	 */
	public void setRecordingSPINFunctions(boolean value) {
		this.recordingSPINFunctions = value;
	}
}
