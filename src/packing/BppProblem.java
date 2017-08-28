package packing;

import problemdomain.ProblemDomain;

public class BppProblem extends ProblemDomain {

	private String filename;

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public InitialSolution evaluate(String heuristics) {
		InitialSolution is = new InitialSolution();
		is.setHeuCom(heuristics);
		is.createSolution(filename);
		return is;
	}

}
