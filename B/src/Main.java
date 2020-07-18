public class Main {

	static boolean[][][] matrix = new boolean[10][3][10];

	static int xLimit = 0;
	static int zLimit = 0;
	static int yLimit = 0;

	static String exist = "[]";
	static String missing = "--";

	public static void main(String[] args) {

		matrix[5][0][5] = true; // center

		System.out.println("Number of combinations: " + search(matrix, 5, 0, 5)); // passing
																					 // center
																					 // point
	}

	static int numberOfCombinations = 0;

	static int search(boolean[][][] matrix, int x, int y, int z) {
		xLimit = matrix.length;
		yLimit = matrix[0].length;
		zLimit = matrix[0][0].length;

		if (check(matrix)) {
			numberOfCombinations++;
			System.out.println("Combination № " + numberOfCombinations);
			printMatrix(matrix);
			System.out.println();
		}

		boolean[][][] new1 = copy(xLimit, yLimit, zLimit, matrix);
		new1 = addRight(x, y, z, new1);

		if (!areEqual(new1, matrix) && numberOfCubes(new1) <= 6)
			search(new1, x + 1, y, z);

		boolean[][][] new2 = copy(xLimit, yLimit, zLimit, matrix);
		new2 = addUp(x, y, z, new2);

		if (!areEqual(new2, matrix) && numberOfCubes(new1) <= 6)
			search(new2, x, y + 1, z);

		boolean[][][] new3 = copy(xLimit, yLimit, zLimit, matrix);
		new3 = addFront(x, y, z, new3);

		if (!areEqual(new3, matrix) && numberOfCubes(new1) <= 6)
			search(new3, x, y, z + 1);

		boolean[][][] new4 = copy(xLimit, yLimit, zLimit, matrix);
		new4 = addLeft(x, y, z, new4);

		if (!areEqual(new4, matrix) && numberOfCubes(new1) <= 6)
			search(new4, x - 1, y, z);

		boolean[][][] new5 = copy(xLimit, yLimit, zLimit, matrix);
		new5 = addDown(x, y, z, new5);

		if (!areEqual(new5, matrix) && numberOfCubes(new1) <= 6)
			search(new5, x, y - 1, z);

		boolean[][][] new6 = copy(xLimit, yLimit, zLimit, matrix);
		new6 = addRear(x, y, z, new6);

		if (!areEqual(new6, matrix) && numberOfCubes(new1) <= 6)
			search(new6, x, y, z - 1);

		return numberOfCombinations;
	}

	static boolean check(boolean[][][] matrix) {
		return numberOfCubes(matrix) == 6 && rule1(matrix) && rule2(matrix) && rule4(matrix) && rule5(matrix) && rule6(matrix);
	}

	static boolean rule1(boolean[][][] matrix) {
		for (int y = 0; y < yLimit; y++)
			for (int z = 0; z < zLimit; z++)
				for (int x = 0; x < xLimit; x++) {

					if (matrix[x][y][z]) {
						if (noCubeIsAlongXandYandZ(x, y, z, matrix))
							return false;
					}
				}
		return true;
	}

	// only 6 cubes
	static int numberOfCubes(boolean[][][] matrix) {
		int numberOfCubes = 0;
		for (int y = 0; y < yLimit; y++)
			for (int z = 0; z < zLimit; z++)
				for (int x = 0; x < xLimit; x++)
					if (matrix[x][y][z])
						numberOfCubes++;

		return numberOfCubes;
	}

	static boolean rule2(boolean[][][] matrix) {
		int numberOfBalconies = 0;

		// start from 1 floor
		for (int y = 1; y < yLimit; y++) {
			for (int z = 0; z < zLimit; z++) {
				for (int x = 0; x < xLimit; x++) {
					// check under
					// it is empty
					if (matrix[x][y][z]) {

						// nothing under this cube
						if (!matrix[x][y - 1][z]) {
							numberOfBalconies++;
							if (y <= 1)
								if (matrix[x][y + 1][z])
									numberOfBalconies++;
						}
					}
				}
			}
		}

		return numberOfBalconies <= 1;
	}

	static boolean rule4(boolean[][][] matrix) {
		// start from 1 floor (0 is ground floor)
		for (int z = 0; z < zLimit; z++) {
			for (int x = 0; x < xLimit; x++) {
				// check under it is empty
				if (matrix[x][1][z] == true) {
					// check surround walls, if there is nothing
					if (noCubeIsAlongXandZ(x, 1, z, matrix))
						if (noCubeIsAlongXandZ(x, 2, z, matrix))
							return false;
				}
			}
		}
		return true;
	}

	static boolean noCubeIsAlongXandZ(int x, int y, int z, boolean[][][] matrix) {
		if (noCubesAlongX(x, y, z, matrix) && noCubesAlongZ(x, y, z, matrix))
			return true;
		return false;
	}

	static boolean noCubeIsAlongXandYandZ(int x, int y, int z, boolean[][][] matrix) {
		if (noCubesAlongX(x, y, z, matrix) && noCubesAlongY(x, y, z, matrix) && noCubesAlongZ(x, y, z, matrix))
			return true;
		return false;
	}

	private static boolean noCubesAlongY(int x, int y, int z, boolean[][][] matrix) {
		if ((y != yLimit - 1 && matrix[x][y + 1][z]) || (y != 0 && matrix[x][y - 1][z]) || (y == yLimit - 1 && matrix[x][y - 1][z]) || (y == 0 && matrix[x][y + 1][z]))
			return false;
		return true;
	}

	private static boolean noCubesAlongX(int x, int y, int z, boolean[][][] matrix) {
		if ((x != xLimit - 1 && matrix[x + 1][y][z]) || (x != 0 && matrix[x - 1][y][z]) || (x == xLimit - 1 && matrix[x - 1][y][z]) || (x == 0 && matrix[x + 1][y][z]))
			return false;
		return true;
	}

	private static boolean noCubesAlongZ(int x, int y, int z, boolean[][][] matrix) {
		if ((z != zLimit - 1 && matrix[x][y][z + 1]) || (z != 0 && matrix[x][y][z - 1]) || (z == zLimit - 1 && matrix[x][y][z - 1]) || (z == 0 && matrix[x][y][z + 1]))
			return false;
		return true;
	}

	static boolean rule5(boolean[][][] matrix) {
		if (tallerThan2(matrix)) {
			if (max3wide(matrix))
				return true;
		} else {
			return true;
		}
		return false;
	}

	static boolean max3wide(boolean[][][] matrix) {
		int minX = 10;
		int maxX = 0;

		int minZ = 10;
		int maxZ = 0;

		for (int y = 0; y < yLimit; y++) {
			for (int z = 0; z < zLimit; z++) {
				for (int x = 0; x < xLimit; x++) {
					if (matrix[x][y][z]) {
						if (x < minX)
							minX = x;
						if (x > maxX)
							maxX = x;

						if (z < minZ)
							minZ = z;
						if (z > maxZ)
							maxZ = z;
					}
				}
			}
		}

		return ((maxX - minX) < 3) && ((maxZ - minZ) < 3);

	}

	static boolean tallerThan2(boolean[][][] matrix) {
		for (int z = 0; z < matrix[0][1].length - 1; z++)
			for (int x = 0; x < matrix.length - 1; x++)
				if (matrix[x][2][z])
					return true;
		return false;
	}

	static boolean rule6(boolean[][][] matrix) {

		int xWidth = 0;
		int zWidth = 0;

		for (int y = 0; y < yLimit; y++) {
			for (int z = 0; z < zLimit; z++) {
				xWidth = 0;
				for (int x = 0; x < xLimit; x++) {
					if (matrix[x][y][z])
						xWidth++;
					if (xWidth > 4)
						return false;
				}
			}
		}

		for (int y = 0; y < yLimit; y++) {
			for (int x = 0; x < xLimit; x++) {
				zWidth = 0;
				for (int z = 0; z < zLimit; z++) {
					if (matrix[x][y][z])
						zWidth++;
					if (zWidth > 4)
						return false;
				}
			}
		}

		return true;
	}

	static boolean[][][] addRight(int x, int y, int z, boolean[][][] matrix) {
		if (x < xLimit - 1)
			matrix[x + 1][y][z] = true;
		return matrix;
	}

	static boolean[][][] addLeft(int x, int y, int z, boolean[][][] matrix) {
		if (x != 0)
			matrix[x - 1][y][z] = true;
		return matrix;
	}

	static boolean[][][] addUp(int x, int y, int z, boolean[][][] matrix) {
		if (y < yLimit - 1)
			matrix[x][y + 1][z] = true;
		return matrix;
	}

	static boolean[][][] addDown(int x, int y, int z, boolean[][][] matrix) {
		if (y != 0)
			matrix[x][y - 1][z] = true;
		return matrix;
	}

	static boolean[][][] addFront(int x, int y, int z, boolean[][][] matrix) {
		if (z != zLimit - 1)
			matrix[x][y][z + 1] = true;
		return matrix;
	}

	static boolean[][][] addRear(int x, int y, int z, boolean[][][] matrix) {
		if (z != 0)
			matrix[x][y][z - 1] = true;
		return matrix;
	}

	static void printMatrix(boolean[][][] matrix) {
		for (int y = yLimit - 1; y >= 0; y--) {
			System.out.println("Floor: " + y);
			for (int z = 0; z < zLimit; z++) {
				for (int x = 0; x < xLimit; x++) {
					if (matrix[x][y][z] == false)
						System.out.print(missing + " ");
					else
						System.out.print(exist + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	static boolean areEqual(boolean[][][] m1, boolean[][][] m2) {
		for (int y = 0; y < yLimit; y++)
			for (int z = 0; z < zLimit; z++)
				for (int x = 0; x < xLimit; x++)
					if (m1[x][y][z] != m2[x][y][z])
						return false;
		return true;
	}

	static void resetMatrix() {
		for (int y = 0; y < yLimit; y++)
			for (int z = 0; z < zLimit; z++)
				for (int x = 0; x < xLimit; x++)
					matrix[x][y][z] = false;
	}

	static boolean[][][] copy(int xl, int yl, int zl, boolean[][][] matrix) {
		boolean[][][] newM = new boolean[xl][yl][zl];
		for (int y = 0; y < yLimit; y++)
			for (int z = 0; z < zLimit; z++)
				for (int x = 0; x < xLimit; x++)
					newM[x][y][z] = matrix[x][y][z];
		return newM;
	}

}
