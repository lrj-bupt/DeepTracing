// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: JVMMetric.proto

package org.tracing.network.language.metric;

import org.tracing.network.language.common.CommandOuterClass;
import org.tracing.network.language.common.Common;

public final class JVMMetricOuterClass {
  private JVMMetricOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_JVMMetricCollection_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_JVMMetricCollection_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_JVMMetric_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_JVMMetric_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_Memory_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_Memory_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_MemoryPool_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_MemoryPool_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_GC_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_GC_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_Thread_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_Thread_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_deeptracing_v1_Class_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_deeptracing_v1_Class_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\017JVMMetric.proto\022\016deeptracing.v1\032\014Commo" +
      "n.proto\032\rCommand.proto\"k\n\023JVMMetricColle" +
      "ction\022*\n\007metrics\030\001 \003(\0132\031.deeptracing.v1." +
      "JVMMetric\022\017\n\007service\030\002 \001(\t\022\027\n\017serviceIns" +
      "tance\030\003 \001(\t\"\201\002\n\tJVMMetric\022\014\n\004time\030\001 \001(\003\022" +
      " \n\003cpu\030\002 \001(\0132\023.deeptracing.v1.CPU\022&\n\006mem" +
      "ory\030\003 \003(\0132\026.deeptracing.v1.Memory\022.\n\nmem" +
      "oryPool\030\004 \003(\0132\032.deeptracing.v1.MemoryPoo" +
      "l\022\036\n\002gc\030\005 \003(\0132\022.deeptracing.v1.GC\022&\n\006thr" +
      "ead\030\006 \001(\0132\026.deeptracing.v1.Thread\022$\n\005cla" +
      "zz\030\007 \001(\0132\025.deeptracing.v1.Class\"T\n\006Memor" +
      "y\022\016\n\006isHeap\030\001 \001(\010\022\014\n\004init\030\002 \001(\003\022\013\n\003max\030\003" +
      " \001(\003\022\014\n\004used\030\004 \001(\003\022\021\n\tcommitted\030\005 \001(\003\"p\n" +
      "\nMemoryPool\022&\n\004type\030\001 \001(\0162\030.deeptracing." +
      "v1.PoolType\022\014\n\004init\030\002 \001(\003\022\013\n\003max\030\003 \001(\003\022\014" +
      "\n\004used\030\004 \001(\003\022\021\n\tcommitted\030\005 \001(\003\"I\n\002GC\022&\n" +
      "\005phase\030\001 \001(\0162\027.deeptracing.v1.GCPhase\022\r\n" +
      "\005count\030\002 \001(\003\022\014\n\004time\030\003 \001(\003\"\315\001\n\006Thread\022\021\n" +
      "\tliveCount\030\001 \001(\003\022\023\n\013daemonCount\030\002 \001(\003\022\021\n" +
      "\tpeakCount\030\003 \001(\003\022 \n\030runnableStateThreadC" +
      "ount\030\004 \001(\003\022\037\n\027blockedStateThreadCount\030\005 " +
      "\001(\003\022\037\n\027waitingStateThreadCount\030\006 \001(\003\022$\n\034" +
      "timedWaitingStateThreadCount\030\007 \001(\003\"a\n\005Cl" +
      "ass\022\030\n\020loadedClassCount\030\001 \001(\003\022\037\n\027totalUn" +
      "loadedClassCount\030\002 \001(\003\022\035\n\025totalLoadedCla" +
      "ssCount\030\003 \001(\003*\244\002\n\010PoolType\022\024\n\020CODE_CACHE" +
      "_USAGE\020\000\022\020\n\014NEWGEN_USAGE\020\001\022\020\n\014OLDGEN_USA" +
      "GE\020\002\022\022\n\016SURVIVOR_USAGE\020\003\022\021\n\rPERMGEN_USAG" +
      "E\020\004\022\023\n\017METASPACE_USAGE\020\005\022\017\n\013ZHEAP_USAGE\020" +
      "\006\022 \n\034COMPRESSED_CLASS_SPACE_USAGE\020\007\022\037\n\033C" +
      "ODEHEAP_NON_NMETHODS_USAGE\020\010\022$\n CODEHEAP" +
      "_PROFILED_NMETHODS_USAGE\020\t\022(\n$CODEHEAP_N" +
      "ON_PROFILED_NMETHODS_USAGE\020\n*\'\n\007GCPhase\022" +
      "\007\n\003NEW\020\000\022\007\n\003OLD\020\001\022\n\n\006NORMAL\020\002B~\n\036org.net" +
      "work.language.jvmmetricP\001Z:skywalking.ap" +
      "ache.org/repo/goapi/collect/language/age" +
      "nt/v3\252\002\035SkyWalking.NetworkProtocol.V3b\006p" +
      "roto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          Common.getDescriptor(),
          CommandOuterClass.getDescriptor(),
        });
    internal_static_deeptracing_v1_JVMMetricCollection_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_deeptracing_v1_JVMMetricCollection_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_JVMMetricCollection_descriptor,
        new String[] { "Metrics", "Service", "ServiceInstance", });
    internal_static_deeptracing_v1_JVMMetric_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_deeptracing_v1_JVMMetric_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_JVMMetric_descriptor,
        new String[] { "Time", "Cpu", "Memory", "MemoryPool", "Gc", "Thread", "Clazz", });
    internal_static_deeptracing_v1_Memory_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_deeptracing_v1_Memory_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_Memory_descriptor,
        new String[] { "IsHeap", "Init", "Max", "Used", "Committed", });
    internal_static_deeptracing_v1_MemoryPool_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_deeptracing_v1_MemoryPool_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_MemoryPool_descriptor,
        new String[] { "Type", "Init", "Max", "Used", "Committed", });
    internal_static_deeptracing_v1_GC_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_deeptracing_v1_GC_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_GC_descriptor,
        new String[] { "Phase", "Count", "Time", });
    internal_static_deeptracing_v1_Thread_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_deeptracing_v1_Thread_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_Thread_descriptor,
        new String[] { "LiveCount", "DaemonCount", "PeakCount", "RunnableStateThreadCount", "BlockedStateThreadCount", "WaitingStateThreadCount", "TimedWaitingStateThreadCount", });
    internal_static_deeptracing_v1_Class_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_deeptracing_v1_Class_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_deeptracing_v1_Class_descriptor,
        new String[] { "LoadedClassCount", "TotalUnloadedClassCount", "TotalLoadedClassCount", });
    Common.getDescriptor();
    CommandOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
