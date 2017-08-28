package packing;

import java.util.ArrayList;

import initialsoln.InitialSoln;

public class InitialSolution extends InitialSoln {

	private BinPacking bpp;
	private String heuristicCombination;
	private double fitness;
	private ArrayList<ArrayList<Integer>> solution;

	@Override
	public int fitter(InitialSoln arg0) {
		if (arg0.getFitness() >= fitness)
			return 1;
		else if (arg0.getFitness() < fitness)
			return -1;
		else
			return 0;
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public String getHeuCom() {
		return heuristicCombination;
	}

	@Override
	public Object getSoln() {
		return solution;
	}

	@Override
	public void setHeuCom(String arg0) {
		this.heuristicCombination = arg0;

	}

	@Override
	public String solnToString() {
		return bpp.toString();
	}

	public void createSolution(String filename) {
		bpp = new BinPacking(filename);
		bpp.createSolution(heuristicCombination);
		fitness = bpp.getFitness();
		solution = bpp.getSolution();
	}

	public void print() {
		bpp.displayBins(solution);
	}

	public int getObjectiveValue() {
		return solution.size();
	}

}
