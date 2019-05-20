package me.steffenjacobs.openhabrequester;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.logging.impl.AvalonLogger;
import org.apache.http.NameValuePair;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.steffenjacobs.openhabrequester.domain.item.ItemDTO;
import me.steffenjacobs.openhabrequester.domain.item.JSONItem;
import me.steffenjacobs.openhabrequester.domain.item.creation.FunctionDTO;
import me.steffenjacobs.openhabrequester.domain.item.creation.ItemCreationDTO;
import me.steffenjacobs.openhabrequester.domain.links.LinksDTO;
import me.steffenjacobs.openhabrequester.domain.rule.RuleDTO;
import me.steffenjacobs.openhabrequester.domain.rule.RuleNameDTO;
import me.steffenjacobs.openhabrequester.domain.rule.RuleNamesDTO;
import me.steffenjacobs.openhabrequester.domain.thing.ChannelsDTO;
import me.steffenjacobs.openhabrequester.domain.thing.ThingDTO;
import me.steffenjacobs.openhabrequester.service.OpenHabItemService;
import me.steffenjacobs.openhabrequester.service.OpenHabLinksService;
import me.steffenjacobs.openhabrequester.service.OpenHabRuleService;
import me.steffenjacobs.openhabrequester.service.OpenHabThingService;
import me.steffenjacobs.openhabrequester.service.generation.ItemGenerationService;
import me.steffenjacobs.openhabrequester.service.generation.RuleGenerationService;

/** @author Steffen Jacobs */
public class Main {

	public static final String OPEN_HAB_URL_WITH_PORT = "http://localhost:8080";

	private static final boolean CREATE_ITEM = false;
	private static final boolean CREATE_RULE = false;
	private static final boolean SHOW_ITEMS = false;
	private static final boolean SHOW_RULES = false;
	private static final boolean SHOW_RULE_NAMES = false;

	private static final boolean ACTUALLY_CREATE_ITEM = false;

	private static final OpenHabItemService itemService = new OpenHabItemService();
	private static final OpenHabRuleService ruleService = new OpenHabRuleService();
	private static final OpenHabThingService thingService = new OpenHabThingService();
	private static final OpenHabLinksService linkService = new OpenHabLinksService();

	private static final ItemGenerationService itemConfigGenerator = new ItemGenerationService();
	private static final RuleGenerationService ruleGenerator = new RuleGenerationService();

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		requestAndPrintThings();

		final String brokerName = "broker";
		if (CREATE_ITEM) {
//			 generateExemplaryCo2Sensor(brokerName);
			System.out.println();
			generateExemplaryTemperatureHumiditySensor(brokerName);
		}

		if (SHOW_ITEMS) {
			requestAndPrintItems();
		}

		if (SHOW_RULE_NAMES) {
			requestAndPrintRuleNames();
		}

		if (SHOW_RULES) {
			requestAndPrintRules();
		}

		if (CREATE_RULE) {
			createNewRule();
		}
	}

	private static void generateExemplaryTemperatureHumiditySensor(final String brokerName) {
		ItemCreationDTO itemTempInside = new ItemCreationDTO();
		itemTempInside.setType("Number");
		itemTempInside.setName("TemperatureInside");
		itemTempInside.setLabel("temperature value inside");

		ItemCreationDTO itemHumidityInside = new ItemCreationDTO();
		itemHumidityInside.setType("Number");
		itemHumidityInside.setName("HumidityInside");
		itemHumidityInside.setLabel("humidity value inside");

		Map<String, ItemCreationDTO> jsonMappings = new HashMap<>();
		jsonMappings.put("temperature", itemTempInside);
		jsonMappings.put("humidity", itemHumidityInside);
		JSONItem jsonItem = new JSONItem("AmbientInsideJSON", jsonMappings);

		System.out.println("Generated Rule:");
		System.out.println(ruleGenerator.generateRule(jsonItem.getName() + " Rule", jsonItem) + "\n");

		System.out.println("\nAdd these lines to " + jsonItem.getName() + ".items:");
		System.out.println(itemConfigGenerator.generateItemConfig(jsonItem, brokerName));

		System.out.println();
		printMqttConfig(brokerName);
	}

	private static void generateExemplaryCo2Sensor(final String brokerName) {
		ItemCreationDTO item = new ItemCreationDTO();
		item.setType("Number");
		item.setName("CO2Inside");
		item.setLabel("CO2 value inside");

		Map<String, ItemCreationDTO> jsonMappings = new HashMap<>();
		jsonMappings.put("co2", item);
		JSONItem jsonItem = new JSONItem("Co2InsideJSON", jsonMappings);
		System.out.println("Generated Rule:");
		System.out.println(ruleGenerator.generateRule(jsonItem.getName() + " Rule", jsonItem) + "\n");

		System.out.println("\nAdd these lines to " + jsonItem.getName() + ".items:");
		System.out.println(itemConfigGenerator.generateItemConfig(jsonItem, brokerName));

		System.out.println();
		printMqttConfig(brokerName);
	}

	private static ItemCreationDTO createNewItem(String brokerName) throws IOException {
		ItemCreationDTO item = new ItemCreationDTO();
		item.setType("Switch");
		item.setName("Outlet5");
		item.setLabel("Outlet 5 ON OFF");

		FunctionDTO function = new FunctionDTO();
		function.setName("EQUAL");

		List<String> params = new ArrayList<>();
		params.add("firstParam");
		params.add("secondParam");
		function.setParams(params);
		item.setFunction(function);

		System.out
				.println("JSON send to " + OPEN_HAB_URL_WITH_PORT + ": " + new ObjectMapper().writeValueAsString(item));

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		if (ACTUALLY_CREATE_ITEM && !itemService.createItem(OPEN_HAB_URL_WITH_PORT, parameters, item)) {
			System.out.println("Could not create item " + item.getName());
		}
		System.out.println();
		return item;
	}

	private static void requestAndPrintItems() {
		System.out.println("----- Selected Items -----");
		List<ItemDTO> availableItems = itemService.requestItems(OPEN_HAB_URL_WITH_PORT);
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for(ItemDTO item : availableItems) {
				listModel.addElement(item.getName()+", "+item.getType()+", "+item.getLabel());
		}
		
		JList<String> inputList = new JList<String>(listModel);
		inputList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane pane = new JScrollPane(inputList);
		
		// ToDo: Als GWT-Tree implementieren
		JOptionPane.showMessageDialog(pane, inputList, "Test", JOptionPane.PLAIN_MESSAGE);
		
		System.out.println(inputList.getSelectedValuesList());
	}

	private static void createNewRule() {
		requestAndPrintRuleNames();

		RuleDTO newRule = new RuleDTO();
		newRule.setName("weather2");
		newRule.setSource(".test-content.");
		System.out.println();

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();

		ruleService.createOrUpdateRule(OPEN_HAB_URL_WITH_PORT, parameters, newRule);
		requestAndPrintRuleNames();
	}

	private static void requestAndPrintRules() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		System.out.println("----- Rules -----");
		List<RuleDTO> rules = ruleService.requestRules(OPEN_HAB_URL_WITH_PORT, parameters);
		for (RuleDTO rule : rules) {
			System.out.println(rule.toString());
		}
		System.out.println();
	}

	private static void requestAndPrintRuleNames() {
		System.out.println("----- Rule Names -----");
		RuleNamesDTO ruleNames = ruleService.requestRuleNames(OPEN_HAB_URL_WITH_PORT);
		for (RuleNameDTO ruleName : ruleNames.getRules()) {
			System.out.println(ruleName.getName());
		}
		System.out.println();
	}

	private static void requestAndPrintThings() {
		System.out.println("----- Selected Things -----");
		List<ThingDTO> availableThings = thingService.requestThings(OPEN_HAB_URL_WITH_PORT);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("TOP");
		
		for(ThingDTO thing : availableThings) {
			DefaultMutableTreeNode thingNode = new DefaultMutableTreeNode(thing.getUID());
			for(ChannelsDTO channel : thing.getChannelsDTO()) {
				DefaultMutableTreeNode channelNode = new DefaultMutableTreeNode(channel.getUid());
				thingNode.add(channelNode);
			}
			top.add(thingNode);
		}

		JTree tree = new JTree(top);
		JScrollPane pane = new JScrollPane(tree);
		JPanel panel = new JPanel();
		
		JFrame frame = new JFrame("Select Channels");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxLayout);
		
		JButton button = new JButton("OK");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath [] selectedPaths = tree.getSelectionPaths();
				for(TreePath selectedPath : selectedPaths) {
					Enumeration<DefaultMutableTreeNode> allNodes = top.breadthFirstEnumeration();
					while (allNodes.hasMoreElements()) {
						DefaultMutableTreeNode nodeObject = allNodes.nextElement();
						if(nodeObject == selectedPath.getLastPathComponent()) {
							System.out.println(nodeObject.getUserObject());
						}
					}
					frame.dispose();
				}
			}
		});
		
		panel.add(pane);
		panel.add(button);
		frame.add(panel);
		frame.pack();
		
		Dimension currentDisplay = Toolkit.getDefaultToolkit().getScreenSize();
		int height = currentDisplay.height;
		int width = currentDisplay.width;
		frame.setLocation(width/2, height/2);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	private static void requestAndPrintLinks() {
		System.out.println("----- Things -----");
		List<LinksDTO> availableLinks = linkService.requestLinks(OPEN_HAB_URL_WITH_PORT);
		for (LinksDTO availableLink : availableLinks) {
			System.out.println(availableLink.toString());
		}
		System.out.println();
	}

	private static void printMqttConfig(String brokerName) {
		System.out.println("Add this line to mqtt.conf:");
		StringBuilder sb = new StringBuilder();
		sb.append(brokerName);
		sb.append(".url=tcp://127.0.0.1:1883");
		System.out.println(sb.toString());
	}


}
