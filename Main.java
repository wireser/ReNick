import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public Main plugin;
	public FileConfiguration configFile;
	public boolean loaded;
	
	public static Essentials esx;
	
	public List<String> messagesKeys;
	public List<String> permissionKeys;
	
	public HashMap<String, String> packs;
	public HashMap<String, String> messages;
	public HashMap<String, String> permissions;
	public HashMap<String, Main.Color> colors;
	
	public String DefaultColor;
	public String CodeDivider;
	public String UsedCommand;
	
	public boolean DebugMode;
	public boolean EmptyCommandResets;
	public boolean UseVanillaColors;
	public boolean UseHEXCodes;
	
	public boolean NickMustBeAlphaNumerical;
	public Integer NickMaxLength;
	
	public boolean UseStylerBold;
	public boolean UseStylerItalic;
	public boolean UseStylerUnderlined;
	public boolean UseStylerStrikethrough;
	public boolean UseStylerMagic;
	public boolean UseStylerReset;
	
	public boolean UseEssentials;
	public boolean UseEssentialsNameChange;
	public boolean UsePlaceHoldersAPI;
	
	@Override
    public void onEnable() {
		
		/* **************************************************************************** */
    	/* ************************** SECTION: INITILIZATION ************************** */
    	/* **************************************************************************** */
		
    	plugin = this;
    	loaded = false;
    	saveDefaultConfig();
    	
    	configFile = plugin.getConfig();
    	
    	packs = new HashMap<String, String>();
    	colors = new HashMap<String, Main.Color>();
    	messages = new HashMap<String, String>();
    	permissions = new HashMap<String, String>();
    	
    	messagesKeys = new ArrayList<String>();
    	permissionKeys = new ArrayList<String>();
    	
    	esx = (Essentials) getServer().getPluginManager().getPlugin("Essentials");

    	messagesKeys.add("noPermissionToUseCommand");
    	messagesKeys.add("noPermissionToUseCommandOnOthers");
    	messagesKeys.add("noPermissionToUseColor");
    	
    	messagesKeys.add("errorPluginIsNotStarted");
    	messagesKeys.add("errorCommandDoesNotExists");
    	messagesKeys.add("errorCommandOnlyInGame");
    	
    	messagesKeys.add("errorNickCommandSyntax");
    	messagesKeys.add("errorCanNotSetNickname");
    	messagesKeys.add("errorUnknownColor");
    	messagesKeys.add("errorMissingText");
    	
    	messagesKeys.add("invalidNameTooShort");
    	messagesKeys.add("invalidNameTooLong");
    	messagesKeys.add("invalidNameSpecial");
    	messagesKeys.add("invalidNameIGN");
    	messagesKeys.add("invalidNameOtherNick");
    	messagesKeys.add("invalidNameOther");
    	
    	messagesKeys.add("nickSetMessage");
    	messagesKeys.add("nickResetMessage");
    	messagesKeys.add("nickSetForYouMessage");
    	
    	
    	permissionKeys.add("VanillaColorsPermission");
    	permissionKeys.add("StylerCodesPermission");
    	permissionKeys.add("HEXCodePermission");
    	permissionKeys.add("CommandNick");
    	permissionKeys.add("CommandNickUseOnOthers");
    	permissionKeys.add("CommandRnConfig");
    	
    	
    	try {
			loadMessages(messagesKeys, messages);
		} catch (IOException ex) {
			log("[ERROR - CONFIG - MESSAGES] " + ex.getMessage());
		}
    	
    	try {
			loadPermissions(permissionKeys, permissions);
		} catch (IOException ex) {
			log("[ERROR - CONFIG - PERMISSIONS] " + ex.getMessage());
		}
    	
    	/* **************************************************************************** */
    	/* ********************************* END ************************************** */
    	/* **************************************************************************** */
    	
    	
    	
    	/* **************************************************************************** */
    	/* ******************** SECTION: LOAD SYSTEM CONFIGURATION ******************** */
    	/* **************************************************************************** */
    	
    	try {
    		DebugMode 			= Objects.requireNonNull( configFile.getBoolean("DebugMode"), "Failed to find variable: DebugMode" );
    		EmptyCommandResets 	= Objects.requireNonNull( configFile.getBoolean("EmptyCommandResets"), "Failed to find variable: EmptyCommandResets" );
    		UseVanillaColors 	= Objects.requireNonNull( configFile.getBoolean("UseVanillaColors"), "Failed to find variable: UseVanillaColors" );
    		UseHEXCodes 		= Objects.requireNonNull( configFile.getBoolean("UseHEXCodes"), "Failed to find variable: UseHEXCodes" );
    		
    		NickMaxLength 			 = Objects.requireNonNull( configFile.getInt("NickMaxLength"), "Failed to find variable: NickMaxLength" );
    		NickMustBeAlphaNumerical = Objects.requireNonNull( configFile.getBoolean("NickMustBeAlphaNumerical"), "Failed to find variable: NickMustBeAlphaNumerical" );
    		
    		DefaultColor 	= Objects.requireNonNull( configFile.getString("DefaultColor"), "Failed to find variable: DefaultColor" );
    		CodeDivider 	= Objects.requireNonNull( configFile.getString("CodeDivider"), "Failed to find variable: CodeDivider" );
    		UsedCommand 	= Objects.requireNonNull( configFile.getString("UsedCommand"), "Failed to find variable: UsedCommand" );
    		
    		UseStylerBold 			= Objects.requireNonNull( configFile.getBoolean("UseStylerBold"), "Failed to find variable: UseStylerBold" );
    		UseStylerItalic 		= Objects.requireNonNull( configFile.getBoolean("UseStylerItalic"), "Failed to find variable: UseStylerItalic" );
    		UseStylerUnderlined 	= Objects.requireNonNull( configFile.getBoolean("UseStylerUnderlined"), "Failed to find variable: UseStylerUnderlined" );
    		UseStylerStrikethrough 	= Objects.requireNonNull( configFile.getBoolean("UseStylerStrikethrough"), "Failed to find variable: UseStylerStrikethrough" );
    		UseStylerMagic 			= Objects.requireNonNull( configFile.getBoolean("UseStylerMagic"), "Failed to find variable: UseStylerMagic" );
    		UseStylerReset 			= Objects.requireNonNull( configFile.getBoolean("UseStylerReset"), "Failed to find variable: UseStylerReset" );

    		UseEssentials 			= Objects.requireNonNull( configFile.getBoolean("UseEssentials"), "Failed to find variable: UseEssentials" );
    		UseEssentialsNameChange = Objects.requireNonNull( configFile.getBoolean("UseEssentialsNameChange"), "Failed to find variable: UseEssentialsNameChange" );
    		UsePlaceHoldersAPI 		= Objects.requireNonNull( configFile.getBoolean("UsePlaceHoldersAPI"), "Failed to find variable: UsePlaceHoldersAPI" );

    		loaded = true;
    	}
    	
    	catch(NullPointerException ex) {
    		log("[ERROR - CONFIG] " + ex.getMessage());
    	}
    	
    	catch(Exception ex) {
    		log("[ERROR - CONFIG] " + ex.getMessage());
    	}
    	
    	/* **************************************************************************** */
    	/* ********************************* END ************************************** */
    	/* **************************************************************************** */
    	
    	
    	
    	/* **************************************************************************** */
    	/* ********************** SECTION: LOAD PACKS AND COLORS ********************** */
    	/* **************************************************************************** */
    	
    	try {
    		
    		if(!loaded)
    			throw new Exception("Plugin is not loaded.");
    		
    		ConfigurationSection packSection = configFile.getConfigurationSection("Packs");
    		Set<String> _packs = packSection.getKeys(false);
    		
    		if(Objects.isNull(_packs) || _packs.isEmpty())
    			throw new IOException("Packs configuration section is empty");
    		
    		for(String pack : _packs) {
    			
    			if(!packSection.getBoolean(pack + ".enabled"))
    				continue;
    			
    			packs.put(pack, packSection.getString(pack + ".permission"));
    			
    			
    			
    			/* *** SECTION: LOAD COLORS FROM PACKS */
    			
    			ConfigurationSection codeSection = packSection.getConfigurationSection(pack + ".codes");
    			Set<String> _codes = codeSection.getKeys(false);
    			
    			for(String code : _codes) {
    				boolean enabled = codeSection.getBoolean(code + ".enabled");
    				String name = codeSection.getString(code + ".name");
    				String permission = codeSection.getString(code + ".permission");
    				String value = codeSection.getString(code + ".value");
    				
    				colors.put(code, new Color(enabled, name, pack, permission, code, value, 1));
    			}
    			
    			/* *** */
    			
    			
    			
    			/* *** SECTION: LOAD COLORS FROM MINECRAFT */

        		if(UseStylerBold) {
        			colors.put("l", new Color(true, "bold", "minecraft", getPermission("StylerCodesPermission"), "l", "l", 0));
        			colors.put("bold", new Color(true, "bold", "minecraft", getPermission("StylerCodesPermission"), "l", "l", 0));
        		}
        		
        		if(UseStylerItalic) {
        			colors.put("o", new Color(true, "italic", "minecraft", getPermission("StylerCodesPermission"), "o", "o", 0));
        			colors.put("italic", new Color(true, "italic", "minecraft", getPermission("StylerCodesPermission"), "o", "o", 0));
        		}
        		
        		if(UseStylerUnderlined) {
        			colors.put("n", new Color(true, "underlined", "minecraft", getPermission("StylerCodesPermission"), "n", "n", 0));
        			colors.put("underlined", new Color(true, "underlined", "minecraft", getPermission("StylerCodesPermission"), "n", "n", 0));
        		}
        		
        		if(UseStylerStrikethrough) {
        			colors.put("m", new Color(true, "strikethrough", "minecraft", getPermission("StylerCodesPermission"), "m", "m", 0));
        			colors.put("strikethrough", new Color(true, "strikethrough", "minecraft", getPermission("StylerCodesPermission"), "m", "m", 0));
        		}
        		
        		if(UseStylerMagic) {
        			colors.put("k", new Color(true, "magic", "minecraft", getPermission("StylerCodesPermission"), "k", "k", 0));
        			colors.put("magic", new Color(true, "magic", "minecraft", getPermission("StylerCodesPermission"), "k", "k", 0));
        		}
        		
        		if(UseStylerReset) {
        			colors.put("r", new Color(true, "reset", "minecraft", getPermission("StylerCodesPermission"), "r", "r", 0));
        			colors.put("reset", new Color(true, "reset", "minecraft", getPermission("StylerCodesPermission"), "r", "r", 0));
        		}
        		
        		if(UseVanillaColors) {
        			colors.put("4", new Color(true, "dark_red", 	"minecraft", getPermission("VanillaColorsPermission"), "4", "AA0000", 0));
        			colors.put("c", new Color(true, "red", 			"minecraft", getPermission("VanillaColorsPermission"), "c", "FF5555", 0));
        			colors.put("6", new Color(true, "gold", 		"minecraft", getPermission("VanillaColorsPermission"), "6", "FFAA00", 0));
        			colors.put("e", new Color(true, "yellow", 		"minecraft", getPermission("VanillaColorsPermission"), "e", "FFFF55", 0));
        			colors.put("2", new Color(true, "dark_green", 	"minecraft", getPermission("VanillaColorsPermission"), "2", "00AA00", 0));
        			colors.put("a", new Color(true, "green", 		"minecraft", getPermission("VanillaColorsPermission"), "a", "55FF55", 0));
        			colors.put("b", new Color(true, "aqua", 		"minecraft", getPermission("VanillaColorsPermission"), "b", "55FFFF", 0));
        			colors.put("3", new Color(true, "dark_aqua", 	"minecraft", getPermission("VanillaColorsPermission"), "3", "00AAAA", 0));
        			colors.put("1", new Color(true, "dark_blue", 	"minecraft", getPermission("VanillaColorsPermission"), "1", "0000AA", 0));
        			colors.put("9", new Color(true, "blue", 		"minecraft", getPermission("VanillaColorsPermission"), "9", "5555FF", 0));
        			colors.put("d", new Color(true, "light_purple", "minecraft", getPermission("VanillaColorsPermission"), "d", "FF55FF", 0));
        			colors.put("5", new Color(true, "dark_purple", 	"minecraft", getPermission("VanillaColorsPermission"), "5", "AA00AA", 0));
        			colors.put("f", new Color(true, "white", 		"minecraft", getPermission("VanillaColorsPermission"), "f", "FFFFFF", 0));
        			colors.put("7", new Color(true, "gray", 		"minecraft", getPermission("VanillaColorsPermission"), "7", "AAAAAA", 0));
        			colors.put("8", new Color(true, "dark_gray", 	"minecraft", getPermission("VanillaColorsPermission"), "8", "555555", 0));
        			colors.put("0", new Color(true, "black", 		"minecraft", getPermission("VanillaColorsPermission"), "0", "000000", 0));
        		}
        		
    			/* *** */
    			
    		}
    		
    	}
    	
    	catch(IOException ex) {
    		log("[ERROR - CONFIG] " + ex.getMessage());
    	}
    	
    	catch(Exception ex) {
    		log("[ERROR - CONFIG] " + ex.getMessage());
    	}
    	
    	/* **************************************************************************** */
    	/* ********************************* END ************************************** */
    	/* **************************************************************************** */
    	
	}
	
	/** SECTION: COMMANDS **/
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
		
		switch(commandLabel.toLowerCase()) {
		
			case "renick":
				try {
					if(!loaded)
						throw new IOException(getMessage("errorPluginIsNotStarted", false));
					
					// Error message shown to console if it doesnt try to set the nickname to a player.
					if(!(sender instanceof Player) && args.length != 2)
						throw new IOException(getMessage("errorCommandOnlyInGame", false));
					
					if(!hasPermission(sender, "CommandNick"))
						throw new IOException(getMessage("noPermissionToUseCommand", false));
					
					// Valid formats:
					// /nick off
					// /nick nickname
					// /nick target nickname (with CommandNickUseOnOthers permission)
					if(args.length == 0 || args.length > 2 || (args.length == 2 && !hasPermission(sender, "CommandNickUseOnOthers")))
						throw new IOException(getMessage("errorNickCommandSyntax", false));
					
					// Try to apply new nickname
					String nickName = translate(sender, args[0]);

					// If we got a valid nickName we set it.
					if(!Objects.isNull(nickName))
						applyNickname(sender.getName(), nickName);
					
					// Impossible error occurred.
					else {
						sendMessage(sender, "invalidNameOther");
						throw new Exception("nickName was null after the first try-catch");
					}
					
					return true;
				}
				
				catch(IllegalStateException ex) {
					if (DebugMode)
				        ex.printStackTrace();

				    String code = ex.getMessage();
					
					if(code.startsWith("0x01::"))
						sendMessage(sender, ChatColor.RED + "Missing text after color code '" + code.replaceFirst("0x01::", "") + "'.", false, true);
					else if (code.startsWith("0x02::"))
						sendMessage(sender, ChatColor.RED + "Unknown color code '" + code.replaceFirst("0x02::", "") + "'.", false, true);
					else if(code.equals("0x03"))
						sendMessage(sender, "invalidNameTooShort");
					else if(code.equals("0x04"))
						sendMessage(sender, "invalidNameTooLong");
					else if(code.equals("0x05"))
						sendMessage(sender, "invalidNameIGN");
					else if(code.equals("0x06"))
						sendMessage(sender, "invalidNameOtherNick");
					else if(code.startsWith("0x07::"))
						sendMessage(sender, ChatColor.RED + "You do not have permission to use the color '" + code.replaceFirst("0x07::", "") + "'.", false, true);
					else if(code.equals("0x08"))
						sendMessage(sender, "invalidNameSpecial");
					else	// Unregistered error occurred.
						sendMessage(sender, "invalidNameOther");
					
					return true;
				}
				
				catch(IOException ex) {
					sendMessage(sender, ex.getMessage(), false, true);
					return true;
				}
				
				catch(Exception ex) {
					logError(sender, ex);
				}
			return true;
			
			case "rnconfig":
				sendMessage(sender, "errorCommandDoesNotExists");
			return true;
				
			default:
				sendMessage(sender, "errorCommandDoesNotExists");
			return true;
			
		}

    }
	
	
	/** SECTION: TRANSLATE **/
	/***
	 * 
	 * @param sender
	 * @param text
	 * @return
	 * @throws Exception
	 */
	private String translate(CommandSender sender, String text) throws IllegalStateException {
        StringBuilder outputString = new StringBuilder();
        StringBuilder rawText = new StringBuilder();
        StringBuilder colorKey = new StringBuilder();

        boolean insideColorCode = false;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (currentChar == CodeDivider.charAt(0)) {
                if (insideColorCode) {
                    // End of color code and start of text
                    processColorKey(colorKey.toString(), outputString, sender);
                    colorKey.setLength(0);
                    insideColorCode = false;
                } else {
                    // Start of color code
                    insideColorCode = true;
                    colorKey.setLength(0); // Reset color key
                }
            } else {
                if (insideColorCode) {
                    // Continue building the color key
                    colorKey.append(currentChar);
                } else {
                    // Append character to both rawText and outputString
                    rawText.append(currentChar);
                    outputString.append(currentChar);
                }
            }
        }

        // If there's any remaining color code to process
        if (colorKey.length() > 0) {
            processColorKey(colorKey.toString(), outputString, sender);
        }

        String finalText = rawText.toString();
        Player shadow = Bukkit.getPlayerExact(finalText);

        if (Objects.isNull(finalText) || finalText.isEmpty() || finalText.isBlank() || finalText.length() < 3) {
            throw new IllegalStateException("0x03");
        }

        if (finalText.length() > NickMaxLength) {
            throw new IllegalStateException("0x04");
        }

        if (!Objects.isNull(shadow) && shadow.hasPlayedBefore() && !shadow.getName().equalsIgnoreCase(sender.getName())) {
            throw new IllegalStateException("0x05");
        }

        for (UUID UUID : esx.getUsers().getAllUserUUIDs()) {
            if (Objects.isNull(UUID) || Objects.isNull(esx.getUser(UUID))) {
                continue;
            }

            String shadowNickname = esx.getUser(UUID).getNickname();

            if (esx.getUser(UUID).getName().equalsIgnoreCase(finalText) && !esx.getUser(UUID).getName().equalsIgnoreCase(sender.getName())) {
                throw new IllegalStateException("0x05");
            }

            if (Objects.isNull(shadowNickname) || shadowNickname.isEmpty() || shadowNickname.isBlank()) {
                continue;
            }

            if (!esx.getUser(UUID).getName().equalsIgnoreCase(sender.getName()) && ChatColor.stripColor(shadowNickname).equalsIgnoreCase(ChatColor.stripColor(finalText))) {
                throw new IllegalStateException("0x06");
            }
        }
        
        if (!isAlphanumericWithUnderscore(finalText)) {
            throw new IllegalStateException("0x08");
        }

        return outputString.toString();
    }

    private void processColorKey(String colorKey, StringBuilder outputString, CommandSender sender) throws IllegalStateException {
        String[] individualColors = colorKey.split(CodeDivider);
        for (String colorPart : individualColors) {
            if (!colorPart.isEmpty()) {
                if (!colors.containsKey(colorPart) || Objects.isNull(colors.get(colorPart)) || !colors.get(colorPart).isEnabled()) {
                    throw new IllegalStateException("0x02::" + colorPart);
                }

                Color color = colors.get(colorPart);
                
                if(!sender.isOp() && !sender.hasPermission(color.getPermission()))
                	throw new IllegalStateException("0x07::" + colorPart);
                
                outputString.append("&").append(color.type == 1 ? "#" : "").append(color.type == 1 ? color.getValue() : color.getSyntax());
            }
        }
    }

    private boolean isAlphanumericWithUnderscore(String str) {
        return str != null && !str.isEmpty() && str.matches("[a-zA-Z0-9_]+");
    }
	
	private void logError(CommandSender sender, Exception ex) {
		if(!DebugMode)
			return;
		
		ex.printStackTrace();
		
		if(sender.isOp())
			sendMessage(sender, ChatColor.GRAY + "[" + ChatColor.RED + "ONLY SHOWN FOR OP" + ChatColor.GRAY + "] Error: () " + ChatColor.YELLOW + ex.getMessage());
	}
	
	/***
	 * 
	 * @param msg
	 */
	private void log(String msg, boolean ignoreDebugMode) {
		if(!ignoreDebugMode && !DebugMode)
			return;
		
		Bukkit.getConsoleSender().sendMessage("[" + plugin.getName() + "] " + msg);
	}
	
	/***
	 * 
	 * @param msg
	 */
	private void log(String msg) {
		log(msg, false);
	}
	
	private boolean hasPermission(CommandSender sender, String permission) {
		if(Objects.isNull(sender) || Objects.isNull(permission) || permission.isEmpty() || permission.isBlank())
			return false;
		
		if(Objects.isNull(permissions) || permissions.isEmpty() || !permissions.containsKey(permission))
			return false;
		
		permission = permissions.get(permission);
		
		if(Objects.isNull(permission) || permission.isEmpty() || permission.isBlank())
			return false;

		return sender.hasPermission(permission);
	}
	
	/***
	 * 
	 * @param player
	 * @param value
	 */
	private void applyNickname(String player, String value) {
		getServer().dispatchCommand(getServer().getConsoleSender(), UsedCommand + " " + player + " " + value);
	}
	
	/***
	 * 
	 * @param list
	 * @param target
	 * @throws IOException 
	 */
	private void loadPermissions(List<String> list, HashMap<String, String> target) throws IOException {
		ConfigurationSection section = configFile.getConfigurationSection("Permissions");
		Set<String> permissions = section.getKeys(false);
		
		if(Objects.isNull(permissions) || messages.isEmpty())
			throw new IOException("Permissions configuration section is empty");
		
		for(String permission : permissions) {
			String value = section.getString(permission);
			
			if(Objects.isNull(value) || value.isEmpty() || value.isBlank())
				continue;
			
			this.permissions.put(permission, value);
		}
	}
	
	/***
	 * 
	 * @param list
	 * @param target
	 * @throws IOException 
	 */
	private void loadMessages(List<String> list, HashMap<String, String> target) throws IOException {
		ConfigurationSection section = configFile.getConfigurationSection("Messages");
		Set<String> messages = section.getKeys(false);
		
		if(Objects.isNull(messages) || messages.isEmpty())
			throw new IOException("Messages configuration section is empty");
		
		for(String message : messages) {
			String value = section.getString(message);
			
			if(Objects.isNull(value) || value.isEmpty() || value.isBlank())
				continue;
			
			this.messages.put(message, ChatColor.translateAlternateColorCodes('&', value));
		}
	}
	
	/***
	 * 
	 * @param key
	 * @param returnNull
	 * @return
	 */
	private String getMessage(String key, boolean returnNull) {
		String designatedReturn;
		
		if(returnNull)
			designatedReturn = null;
		else
			designatedReturn = new String("");
		
		if(Objects.isNull(messages) || messages.isEmpty())
			return designatedReturn;
		
		if(Objects.isNull(key) || key.isEmpty() || key.isBlank())
			return designatedReturn;
		
		if(!messages.containsKey(key))
			return designatedReturn;
		
		designatedReturn = messages.get(key);
		
		return designatedReturn;
	}
	
	/***
	 * 
	 * @param key
	 * @return
	 */
	private String getPermission(String key) {
		if(Objects.isNull(permissions) || permissions.isEmpty())
			return null;
		
		if(Objects.isNull(key) || key.isEmpty() || key.isBlank())
			return null;
		
		if(!permissions.containsKey(key))
			return null;

		return permissions.get(key);
	}
	
	private void sendMessage(CommandSender target, String key) {
	    sendMessage(target, key, true, false);
	}

	private void sendMessage(CommandSender target, String key, boolean displayNull, boolean keyIsTheMessage) {
	    if (Objects.isNull(key) || key.isEmpty() || key.isBlank()) {
	        return;
	    }

	    String msg = keyIsTheMessage ? key : getMessage(key, true);
	    String designatedReturn;

	    if (displayNull) {
	        designatedReturn = ChatColor.DARK_RED + "[" + plugin.getName() + "] " + "%" + key + "%";
	    } else {
	        designatedReturn = null;
	    }

	    if (Objects.isNull(msg)) {
	        msg = designatedReturn;
	    }

	    if (!Objects.isNull(msg)) {
	        target.sendMessage(msg);
	    }
	}


	/***
	 * 
	 */
	/** SECTION: Color class **/
	public class Color {
	    private boolean enabled;
	    private String name;
	    private String pack;
	    private String permission;
	    private String syntax;
	    private String value;
	    private int type;

	    public Color(boolean enabled, String name, String pack, String permission, String syntax, String value, int type) {
	        this.enabled = enabled;
	        this.name = name;
	        this.pack = pack;
	        this.permission = permission;
	        this.syntax = syntax;
	        this.value = value;
	        this.type = type;
	    }
	    
	    public boolean isEnabled() { return enabled; }
	    public void setEnabled(boolean enabled) { this.enabled = enabled; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getPack() { return pack; }
	    public void setPack(String pack) { this.pack = pack; }

	    public String getPermission() { return permission; }
	    public void setPermission(String permission) { this.permission = permission; }

	    public String getSyntax() { return syntax; }
	    public void setSyntax(String syntax) { this.syntax = syntax; }

	    public String getValue() { return value; }
	    public void setValue(String value) { this.value = value; }
	    
	    public int getType() { return type; }
	    public void setType(int type) { this.type = type; }
	    
	}
	
}
