export interface ProfilingResult {
  /** Unique identifier for the result (from database) */
  id: number;

  /** Time taken to execute the code (in milliseconds) */
  cpuTimeMs: number;

  /** Peak memory used during code execution (in megabytes) */
  memoryUsedMb: number;

  /** Estimated energy consumption for this run (in joules) */
  estimatedEnergyJoules: number;

  /** Exit code of the executed program (0 = success) */
  exitCode: number;

  /** Green score calculated based on efficiency metrics (0–100) */
  greenScore: number;

  /** Suggestions for improving code efficiency */
  suggestions: string[];

  /** The geographical region where the code was analyzed/executed */
  executionRegion: string;

  /** Carbon intensity of the execution region (gCO2/kWh) */
  carbonIntensity: number;

  /** Timestamp of when the result was generated */
  createdAt: string;
}
