package ir;

import java.util.ArrayList;

public class Annotation {
	
	
	private String idDoc ;
	private ArrayList<Position> positions=new ArrayList<Position>();
	
	public Annotation (String idDoc)
	{
		this.idDoc=idDoc;
	}
	
	public Annotation (String idDoc, ArrayList<Position> positions)
	{
		this.idDoc=idDoc;
		this.positions= positions;
	}
	
	public void addPosition(Position position)
	{
		positions.add(position);
	}
	
	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position> positions) {
		this.positions = positions;
	}

	
	
	@Override
	public String toString() {
		return "Annotation [idDoc=" + idDoc + ", positions=" + positions + "]";
	}

}
