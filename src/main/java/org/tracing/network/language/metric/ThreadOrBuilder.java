// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: JVMMetric.proto

package org.tracing.network.language.metric;

public interface ThreadOrBuilder extends
    // @@protoc_insertion_point(interface_extends:deeptracing.v1.Thread)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 liveCount = 1;</code>
   * @return The liveCount.
   */
  long getLiveCount();

  /**
   * <code>int64 daemonCount = 2;</code>
   * @return The daemonCount.
   */
  long getDaemonCount();

  /**
   * <code>int64 peakCount = 3;</code>
   * @return The peakCount.
   */
  long getPeakCount();

  /**
   * <code>int64 runnableStateThreadCount = 4;</code>
   * @return The runnableStateThreadCount.
   */
  long getRunnableStateThreadCount();

  /**
   * <code>int64 blockedStateThreadCount = 5;</code>
   * @return The blockedStateThreadCount.
   */
  long getBlockedStateThreadCount();

  /**
   * <code>int64 waitingStateThreadCount = 6;</code>
   * @return The waitingStateThreadCount.
   */
  long getWaitingStateThreadCount();

  /**
   * <code>int64 timedWaitingStateThreadCount = 7;</code>
   * @return The timedWaitingStateThreadCount.
   */
  long getTimedWaitingStateThreadCount();
}
