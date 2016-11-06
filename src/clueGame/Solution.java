package clueGame;

public class Solution {

	public String person;
	public String room;
	public String weapon;
	
	public Solution() {
		// TODO Auto-generated constructor stub
	}

	public Solution(String person, String room, String weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public String getPerson() {
		return person;
	}
	public String getRoom() {
		return room;
	}
	public String getWeapon() {
		return weapon;
	}

	public boolean equals(Solution s){
		if (s.getPerson().equals(person)){
			if (s.getRoom().equals(room)){
				if (s.getWeapon().equals(weapon)){
					return true;
				}
			}
		}
		return false;
	}
}
