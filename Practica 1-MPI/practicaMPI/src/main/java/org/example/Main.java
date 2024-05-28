import mpi.MPI;
import mpi.MPIException;

public class Main {

    // Variable para controlar si la ejecución secuencial ya se realizó
    private static boolean isSequentialExecuted = false;

    public static void main(String[] args) throws MPIException {
        // Inicializar MPI
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int N = 2; // Tamaño de las matrices (cambiar según sea necesario) 2x2

        // Declaración de las matrices:
        double[][] A = null;
        double[][] B = null;
        double[][] C = new double[N][N];

        long startTime = System.currentTimeMillis();


        //1. Arquitectura de Memoria Compartida:
        //a. Inicialice las matrices A y B solo en el proceso raíz (rango 0).
        if (rank == 0) {
            A = new double[][]{
                    {1, 2},
                    {3, 4}
            };

            B = new double[][]{
                    {5, 6},
                    {7, 8}
            };

        }
        ///////////////////

        // Tomar tiempo después de la inicialización de las matrices
        long matrixInitTime = System.currentTimeMillis() - startTime;

        //2. Arquitectura de Memoria Distribuida:
        //a. Distribuya las filas de las matrices A y B a todos los procesos utilizando la
        //función Bcast() de MPI.

        int rowsPerProcess = N / size; // Calcular filas por proceso

        // Crear matrices locales para recibir los datos distribuidos
        double[] flatA = new double[N * N];
        double[] flatB = new double[N * N];
        double[] flatLocalA = new double[rowsPerProcess * N];
        double[] flatLocalC = new double[rowsPerProcess * N];

        // Proceso raíz convierte las matrices a formato unidimensional
        if (rank == 0) {
            flattenMatrix(A, flatA);
            flattenMatrix(B, flatB);
        }

        // Distribuir las filas de A y B a todos los procesos
        MPI.COMM_WORLD.Scatter(flatA, 0, rowsPerProcess * N, MPI.DOUBLE, flatLocalA, 0, rowsPerProcess * N, MPI.DOUBLE, 0);
        MPI.COMM_WORLD.Bcast(flatB, 0, N * N, MPI.DOUBLE, 0);

        // Convertir B a formato bidimensional para el cálculo local
        double[][] localB = new double[N][N];
        unflattenMatrix(flatB, localB, N);


        //3. Paso de Mensajes:
        //a. Cada proceso calculará una porción de la matriz resultante C utilizando el
        //algoritmo de multiplicación de matrices.
        //b. Utilice mensajes para comunicar las porciones de las matrices A, B y C
        //entre los procesos.


        // Calcular la porción de C localmente
        double[][] localA = new double[rowsPerProcess][N];
        unflattenMatrix(flatLocalA, localA, rowsPerProcess);

        double[][] localC = new double[rowsPerProcess][N];
        for (int i = 0; i < rowsPerProcess; i++) {
            for (int j = 0; j < N; j++) {
                localC[i][j] = 0;
                for (int k = 0; k < N; k++) {
                    localC[i][j] += localA[i][k] * localB[k][j];
                }
            }
        }

        // Convertir la porción local de C a formato unidimensional para Gather
        flattenMatrix(localC, flatLocalC);

        // Recoger los resultados en el proceso raíz
        double[] flatC = new double[N * N];
        MPI.COMM_WORLD.Gather(flatLocalC, 0, rowsPerProcess * N, MPI.DOUBLE, flatC, 0, rowsPerProcess * N, MPI.DOUBLE, 0);

        // Proceso raíz convierte la matriz C a formato bidimensional
        if (rank == 0) {
            unflattenMatrix(flatC, C, N);

            // Imprimir el resultado
            System.out.println("Resultado de la multiplicación de matrices paralela con MPJ:");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(C[i][j] + " ");
                }
                System.out.println();
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Finalizar MPI
        MPI.Finalize();


        //5. Pruebas y Verificación:
        //a. Verifique la corrección del programa probándolo con matrices de
        //diferentes tamaños.b. Compare los resultados con la multiplicación de matrices secuencial para
        //garantizar la precisión de la implementación paralela.


        // Verificar si este es el proceso raíz y si aún no hemos ejecutado la multiplicación secuencial
        if (rank == 0 && !isSequentialExecuted) {
            System.out.println("Tiempo de ejecución MPJ: " + duration + " milisegundos");

            double[][] As = {
                    {1, 2},
                    {3, 4}
            };

            double[][] Bs = {
                    {5, 6},
                    {7, 8}
            };

            int Ns = As.length;
            int Ms = Bs[0].length;
            int Ks = Bs.length; // También es el número de filas de A y el número de columnas de B

            double[][] Cs = new double[Ns][Ms];

            // Multiplicación de matrices
            for (int i = 0; i < Ns; i++) {
                for (int j = 0; j < Ms; j++) {
                    for (int k = 0; k < Ks; k++) {
                        Cs[i][j] += As[i][k] * Bs[k][j];
                    }
                }
            }

            // Imprimir la matriz resultante C
            System.out.println("Resultado de la multiplicación de matrices secuencial:");
            for (int i = 0; i < Ns; i++) {
                for (int j = 0; j < Ms; j++) {
                    System.out.print(Cs[i][j] + " ");
                }
                System.out.println();
            }

            // Marcar que la ejecución secuencial ya se ha realizado
            isSequentialExecuted = true;

        }


    }

    // Método para convertir una matriz bidimensional en unidimensional
    public static void flattenMatrix(double[][] src, double[] dest) {
        int index = 0;
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[0].length; j++) {
                dest[index++] = src[i][j];
            }
        }
    }

    // Método para convertir una matriz unidimensional en bidimensional
    public static void unflattenMatrix(double[] src, double[][] dest, int rows) {
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < dest[0].length; j++) {
                dest[i][j] = src[index++];
            }
        }
    }


}
