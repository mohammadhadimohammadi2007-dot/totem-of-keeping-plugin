# 🛡️ Totem of Keeping (Paper 1.21+)

A PvP-focused Minecraft Paper plugin that introduces a custom **Totem of Keeping** system, allowing players to preserve their inventory on death with balanced mechanics, anti-abuse protection, and configurable drop logic.

---

## 📸 Preview

<!-- Replace with your own GIF or screenshot -->

![Demo](https://via.placeholder.com/800x400?text=Totem+of+Keeping+Demo)

---

## ✨ Features

### ⚔️ PvP-Based Mechanics

* Activates **only on PvP deaths**
* Supports **indirect PvP kills** (lava, fall, crystals, etc.)
* Optional restriction: **End-only usage**

---

### 🎁 Totem Drop System

* Players with permission `rank.sponsor` can drop a totem when killed
* Fully configurable:

    * Drop chance (default: 40%)
    * Cooldown per player (default: 30 minutes)
* Includes **anti-farming system** to prevent abuse between the same players

---

### 🛡️ Totem Behavior

When a player dies with a Totem of Keeping:

* Consumes **exactly one totem**
* Prevents item drops
* Preserves inventory and experience
* Clears drops to avoid duplication exploits
* Sends activation feedback message

---

## ⚙️ Commands

| Command                        | Description               | Permission    |
| ------------------------------ | ------------------------- | ------------- |
| `/totemgive <player> [amount]` | Give Totem(s) to a player | `totem.admin` |
| `/totemreload`                 | Reload plugin config      | `totem.admin` |

---

## 💎 Item Details

* Based on **Totem of Undying**
* Custom name: `§dTotem of Keeping`
* Custom lore
* Glowing effect (hidden enchantment)
* Identified via **PersistentDataContainer** (safe & unique)

---

## 🔧 Configuration

```yaml
drop-chance: 0.4
cooldown: 1800
end-only: false

messages:
  received: "§aYou received a Totem of Keeping!"
  activated: "§dYour Totem of Keeping saved your inventory!"
```

---

## 🔐 Permissions

```yaml
rank.sponsor   # Eligible to drop totems on death
totem.admin    # Access to commands
```

---

## 🧠 Technical Highlights

* Event-driven architecture using Bukkit/Paper API
* Safe inventory manipulation to prevent duplication exploits
* Persistent item identification via `NamespacedKey` & `PersistentDataContainer`
* PvP detection including indirect damage sources
* Cooldown and anti-farming system design

---

## 🎯 Use Case

Designed for competitive PvP servers where inventory loss mechanics need to be balanced with fair gameplay and anti-abuse protections.

---

## 📦 Installation

1. Build the plugin:

   ```bash
   mvn clean package
   ```
2. Place the generated `.jar` file into your server's `/plugins` folder
3. Start or reload the server
4. Configure settings in `config.yml` if needed

---

## 📥 Download

Download the latest version from the **Releases** section of this repository.

---

## ⚠️ Notes

* Cooldowns are currently **memory-based** (reset on server restart)
* Designed for **Paper 1.21+**
* Recommended for PvP / SMP environments

---

## 🚀 Future Improvements

* Persistent cooldown storage (file or database)
* GUI-based management
* PlaceholderAPI integration
* Advanced anti-alt detection

---

## 👤 Author

Mohammad Hadi Mohammadi
Minecraft Plugin Developer

---

## 📜 License

This project is licensed under the MIT License.
