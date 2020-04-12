package mi6.parser.pane;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import mi6.parser.controller.Processor;
import mi6.parser.utils.Icons;

public class MainWindowDialog extends JFrame implements Panel{

	private static final long serialVersionUID = 1L;
	
	private Processor processor;
	private MainWindow main;
	private ToolBar toolbar;

	public MainWindowDialog() {
		setTitle("JSON to CSV Converter V-1.0.0");
		initComponents();
		initLayout();
		initListeners();
		pack();
		setLocationRelativeTo(null);
	}
	
	@Override
	public void initComponents() {
		processor = new Processor();
		main = new MainWindow(processor);
		toolbar = new ToolBar();
		
	}

	@Override
	public void initLayout() {
		setLayout(new BorderLayout());
		setIconImage(Icons.getConverter());
		add(toolbar, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		
	}

	@Override
	public void initListeners() {
		toolbar.addPropertyChangeListener(main);
		toolbar.addPropertyChangeListener(processor);
		
	}
}
