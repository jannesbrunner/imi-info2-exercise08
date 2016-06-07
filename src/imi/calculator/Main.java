package imi.calculator;

public class Main {

	private CalcEngine engine;
    private HexUserInterface gui;

    /**
     * Create a new calculator and show it.
     * @throws InterruptedException 
     */
    public Main() throws InterruptedException
    {
        // engine = new CalcEngine(); // Old System
    	engine = new CalcEngine();
        gui = new HexUserInterface(engine);
    }

    /**
     * In case the window was closed, show it again.
     */
    public void show()
    {
        gui.setVisible(true);
    }
    
    public static void main(String[] args) throws InterruptedException {
		Main myMain = new Main();
		myMain.show();
		
		}
    
}
