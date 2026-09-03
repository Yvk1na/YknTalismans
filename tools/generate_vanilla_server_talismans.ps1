param(
    [string]$OutputDirectory = (Join-Path (Split-Path -Parent $PSScriptRoot) 'talismans\ykn_vanilla_server')
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$tiers = @(
    [pscustomobject]@{ Roman = 'I'; Core = 'ecoitems:talisman_core_1 ? ender_eye' }
    [pscustomobject]@{ Roman = 'II'; Core = 'ecoitems:talisman_core_1 ? ender_eye' }
    [pscustomobject]@{ Roman = 'III'; Core = 'ecoitems:talisman_core_2 ? heart_of_the_sea' }
    [pscustomobject]@{ Roman = 'IV'; Core = 'ecoitems:talisman_core_2 ? heart_of_the_sea' }
    [pscustomobject]@{ Roman = 'V'; Core = 'ecoitems:talisman_core_3 ? nether_star' }
)

$rarities = @(
    [pscustomobject]@{ Color = '&8'; Lore = '&8普通护符' }
    [pscustomobject]@{ Color = '&a'; Lore = '&a少见的护符' }
    [pscustomobject]@{ Color = '&9'; Lore = '&9稀有护符' }
    [pscustomobject]@{ Color = '&5'; Lore = '&5史诗护符' }
    [pscustomobject]@{ Color = '&6'; Lore = '&6传说护符' }
    [pscustomobject]@{ Color = '&d'; Lore = '&d神话护符' }
)

$families = @(
    [pscustomobject]@{
        Id = 'life_crystal'; Name = '生命结晶'; TierCount = 5; BaseRarity = 1; Category = '原版属性'; Base = 'apple'
        Icons = @('redstone', 'golden_apple', 'enchanted_golden_apple', 'totem_of_undying', 'nether_star')
        Materials = @('redstone_block', 'gold_block', 'ghast_tear', 'enchanted_golden_apple', 'nether_star')
        Effect = [pscustomobject]@{ Id = 'bonus_health'; Arg = 'health'; Values = @('1', '2', '4', '8', '14'); Lore = '最大生命'; LoreValues = @('+1', '+2', '+4', '+8', '+14'); Color = '&c'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'iron_guard'; Name = '钢铁护印'; TierCount = 4; BaseRarity = 1; Category = '原版属性'; Base = 'iron_ingot'
        Icons = @('iron_nugget', 'iron_chestplate', 'diamond_chestplate', 'netherite_chestplate')
        Materials = @('iron_ingot', 'iron_block', 'diamond_block', 'netherite_ingot')
        Effect = [pscustomobject]@{ Id = 'armor'; Arg = 'points'; Values = @('1', '2', '4', '6'); Lore = '护甲值'; LoreValues = @('+1', '+2', '+4', '+6'); Color = '&9'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'adamant_plate'; Name = '坚钢铭牌'; TierCount = 3; BaseRarity = 1; Category = '原版属性'; Base = 'obsidian'
        Icons = @('chainmail_chestplate', 'turtle_helmet', 'netherite_scrap')
        Materials = @('iron_block', 'diamond_block', 'netherite_scrap')
        Effect = [pscustomobject]@{ Id = 'armor_toughness'; Arg = 'points'; Values = @('1', '2', '3'); Lore = '护甲韧性'; LoreValues = @('+1', '+2', '+3'); Color = '&9'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'fleet_anklet'; Name = '迅捷脚环'; TierCount = 4; BaseRarity = 0; Category = '原版属性'; Base = 'sugar'
        Icons = @('leather_boots', 'golden_boots', 'diamond_boots', 'elytra')
        Materials = @('feather', 'rabbit_foot', 'phantom_membrane', 'breeze_rod')
        Effect = [pscustomobject]@{ Id = 'movement_speed_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.09', '1.14'); Lore = '移动速度'; LoreValues = @('+2%', '+5%', '+9%', '+14%'); Color = '&f'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'clockwork_core'; Name = '发条核心'; TierCount = 3; BaseRarity = 1; Category = '原版属性'; Base = 'redstone'
        Icons = @('copper_ingot', 'clock', 'golden_sword')
        Materials = @('copper_ingot', 'clock', 'gold_ingot')
        Effect = [pscustomobject]@{ Id = 'attack_speed_multiplier'; Arg = 'multiplier'; Values = @('1.03', '1.07', '1.12'); Lore = '攻击速度'; LoreValues = @('+3%', '+7%', '+12%'); Color = '&e'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'miner_pulse'; Name = '矿工脉冲器'; TierCount = 4; BaseRarity = 0; Category = '原版属性'; Base = 'cobblestone'
        Icons = @('wooden_pickaxe', 'iron_pickaxe', 'diamond_pickaxe', 'netherite_pickaxe')
        Materials = @('iron_ingot', 'gold_ingot', 'diamond', 'ancient_debris')
        Effect = [pscustomobject]@{ Id = 'mining_speed_multiplier'; Arg = 'multiplier'; Values = @('1.03', '1.07', '1.12', '1.18'); Lore = '挖掘速度'; LoreValues = @('+3%', '+7%', '+12%', '+18%'); Color = '&e'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'spring_charm'; Name = '跃动弹簧'; TierCount = 1; BaseRarity = 0; Category = '原版属性'; Base = 'slime_ball'
        Icons = @('slime_ball', 'rabbit_foot', 'wind_charge')
        Materials = @('sugar', 'rabbit_foot', 'breeze_rod')
        Effect = [pscustomobject]@{ Id = 'jump_strength_multiplier'; Arg = 'multiplier'; Values = @('1.05', '1.10', '1.18'); Lore = '跳跃力量'; LoreValues = @('+5%', '+10%', '+18%'); Color = '&a'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'diver_pearl'; Name = '潜水者之珠'; TierCount = 2; BaseRarity = 0; Category = '原版属性'; Base = 'kelp'
        Icons = @('prismarine_shard', 'turtle_helmet', 'conduit')
        Materials = @('prismarine_shard', 'nautilus_shell', 'heart_of_the_sea')
        Effect = [pscustomobject]@{ Id = 'oxygen_bonus'; Arg = 'amount'; Values = @('1', '2', '4'); Lore = '水下呼吸效率'; LoreValues = @('+1', '+2', '+4'); Color = '&b'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'landing_rune'; Name = '轻落符文'; TierCount = 1; BaseRarity = 0; Category = '原版属性'; Base = 'white_wool'
        Icons = @('feather', 'hay_block', 'phantom_membrane')
        Materials = @('feather', 'hay_block', 'phantom_membrane')
        Effect = [pscustomobject]@{ Id = 'safe_fall_distance'; Arg = 'distance'; Values = @('1', '2', '4'); Lore = '安全坠落距离'; LoreValues = @('+1 格', '+2 格', '+4 格'); Color = '&f'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'builder_reach'; Name = '筑造者量尺'; TierCount = 3; BaseRarity = 1; Category = '原版属性'; Base = 'stick'
        Icons = @('stick', 'bamboo', 'end_rod')
        Materials = @('copper_ingot', 'bamboo', 'end_rod')
        Effect = [pscustomobject]@{ Id = 'block_reach'; Arg = 'reach'; Values = @('0.5', '1.0', '1.5'); Lore = '方块交互距离'; LoreValues = @('+0.5 格', '+1 格', '+1.5 格'); Color = '&e'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'war_contract'; Name = '战争契约'; TierCount = 5; BaseRarity = 1; Category = '服务器增益'; Base = 'rotten_flesh'
        Icons = @('stone_sword', 'iron_sword', 'diamond_sword', 'netherite_sword', 'mace')
        Materials = @('iron_ingot', 'blaze_rod', 'diamond', 'heavy_core', 'nether_star')
        Effect = [pscustomobject]@{ Id = 'damage_multiplier'; Arg = 'multiplier'; Values = @('1.01', '1.03', '1.06', '1.10', '1.16'); Lore = '造成伤害'; LoreValues = @('+1%', '+3%', '+6%', '+10%', '+16%'); Color = '&c'; Triggers = @('melee_attack', 'bow_attack', 'trident_attack') }
    }
    [pscustomobject]@{
        Id = 'scholar_badge'; Name = '学者徽章'; TierCount = 4; BaseRarity = 0; Category = '服务器增益'; Base = 'paper'
        Icons = @('book', 'experience_bottle', 'enchanting_table', 'echo_shard')
        Materials = @('lapis_lazuli', 'experience_bottle', 'amethyst_shard', 'echo_shard')
        Effect = [pscustomobject]@{ Id = 'xp_multiplier'; Arg = 'multiplier'; Values = @('1.03', '1.07', '1.13', '1.20'); Lore = '原版经验获取'; LoreValues = @('+3%', '+7%', '+13%', '+20%'); Color = '&a'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'merchant_seal'; Name = '商会印章'; TierCount = 4; BaseRarity = 2; Category = '服务器增益'; Base = 'gold_nugget'
        Icons = @('emerald', 'emerald_block', 'diamond', 'nether_star')
        Materials = @('emerald', 'gold_block', 'diamond_block', 'nether_star')
        Effect = [pscustomobject]@{ Id = 'sell_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.09', '1.14'); Lore = '商店出售价格'; LoreValues = @('+2%', '+5%', '+9%', '+14%'); Color = '&6'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'feast_token'; Name = '盛宴餐券'; TierCount = 1; BaseRarity = 0; Category = '服务器增益'; Base = 'wheat'
        Icons = @('bread', 'golden_carrot', 'cake')
        Materials = @('cooked_beef', 'golden_carrot', 'cake')
        Effect = [pscustomobject]@{ Id = 'food_multiplier'; Arg = 'multiplier'; Values = @('1.05', '1.10', '1.20'); Lore = '食物恢复量'; LoreValues = @('+5%', '+10%', '+20%'); Color = '&6'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'brewer_hourglass'; Name = '酿造沙漏'; TierCount = 3; BaseRarity = 0; Category = '服务器增益'; Base = 'glass_bottle'
        Icons = @('glass_bottle', 'blaze_powder', 'brewing_stand')
        Materials = @('nether_wart', 'blaze_powder', 'ghast_tear')
        Effect = [pscustomobject]@{ Id = 'brew_time_multiplier'; Arg = 'multiplier'; Values = @('0.95', '0.85', '0.70'); Lore = '酿造速度'; LoreValues = @('+5%', '+15%', '+30%'); Color = '&d'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'angler_reel'; Name = '垂钓者线轮'; TierCount = 3; BaseRarity = 1; Category = '服务器增益'; Base = 'string'
        Icons = @('fishing_rod', 'nautilus_shell', 'trident')
        Materials = @('cod', 'nautilus_shell', 'heart_of_the_sea')
        Effect = [pscustomobject]@{ Id = 'reel_speed_multiplier'; Arg = 'multiplier'; Values = @('1.10', '1.25', '1.50'); Lore = '收杆速度'; LoreValues = @('+10%', '+25%', '+50%'); Color = '&b'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'trader_writ'; Name = '行商凭证'; TierCount = 2; BaseRarity = 1; Category = '服务器增益'; Base = 'paper'
        Icons = @('emerald', 'bell', 'emerald_block')
        Materials = @('emerald', 'bell', 'diamond')
        Effect = [pscustomobject]@{ Id = 'villager_trade_multiplier'; Arg = 'multiplier'; Values = @('0.98', '0.94', '0.88'); Lore = '村民交易消耗'; LoreValues = @('-2%', '-6%', '-12%'); Color = '&a'; Triggers = @() }
    }
    [pscustomobject]@{
        Id = 'bounty_satchel'; Name = '赏金行囊'; TierCount = 5; BaseRarity = 1; Category = '服务器增益'; Base = 'leather'
        Icons = @('chest', 'barrel', 'ender_chest', 'shulker_box', 'dragon_egg')
        Materials = @('gold_ingot', 'diamond', 'ender_eye', 'shulker_shell', 'nether_star')
        Effect = [pscustomobject]@{ Id = 'multiply_drops'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.10', '1.16', '1.25'); Lore = '物品掉落数量'; LoreValues = @('+2%', '+5%', '+10%', '+16%', '+25%'); Color = '&6'; Triggers = @('block_item_drop', 'entity_item_drop', 'catch_fish', 'shear') }
    }
)

New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
Get-ChildItem -LiteralPath $OutputDirectory -File -Filter '*.yml' -ErrorAction SilentlyContinue | Remove-Item -Force
$utf8NoBom = [System.Text.UTF8Encoding]::new($false)
$written = 0

foreach ($family in $families) {
    for ($index = 0; $index -lt $family.TierCount; $index++) {
        $tierNumber = $index + 1
        $tier = $tiers[$index]
        $baseRarity = $family.BaseRarity
        $rarity = $rarities[$baseRarity + $index]
        $id = "$($family.Id)_$tierNumber"
        $effect = $family.Effect
        $lines = [System.Collections.Generic.List[string]]::new()

        $displayTier = if ($family.TierCount -eq 1) { '' } else { " $($tier.Roman)" }
        $lines.Add("name: `"$($rarity.Color)$($family.Name)$displayTier`"")
        $lines.Add('description:')
        $lines.Add("  - `"$($effect.Color)$($effect.Lore) $($effect.LoreValues[$index])`"")
        if ($family.TierCount -gt 1) {
            $lines.Add('  - ""')
            $lines.Add('  - "&8同系列仅最高阶生效"')
        }
        $lines.Add('  - ""')
        $lines.Add("  - `"$($rarity.Lore)`"")
        if ($tierNumber -gt 1) {
            $lines.Add("higherLevelOf: $($family.Id)_$($tierNumber - 1)")
        }
        $lines.Add("item: $($family.Icons[$index])")
        $lines.Add('craftable: true')
        $lines.Add('crafting-permission: []')
        $lines.Add('shapeless: false')
        $lines.Add('recipe:')

        $corner = if ($tierNumber -eq 1) { $family.Base } else { "talismans:$($family.Id)_$($tierNumber - 1)" }
        $side = $family.Materials[$index]
        $lines.Add("  - $corner")
        $lines.Add("  - $side")
        $lines.Add("  - $corner")
        $lines.Add('')
        $lines.Add("  - $side")
        $lines.Add("  - $($tier.Core)")
        $lines.Add("  - $side")
        $lines.Add('')
        $lines.Add("  - $corner")
        $lines.Add("  - $side")
        $lines.Add("  - $corner")
        $lines.Add('effects:')
        $lines.Add("  - id: $($effect.Id)")
        $lines.Add('    args:')
        $lines.Add("      $($effect.Arg): $($effect.Values[$index])")
        if ($effect.Triggers.Count -gt 0) {
            $lines.Add('    triggers:')
            foreach ($trigger in $effect.Triggers) {
                $lines.Add("      - $trigger")
            }
        }
        $lines.Add('conditions: []')

        $target = Join-Path $OutputDirectory "$id.yml"
        [System.IO.File]::WriteAllLines($target, $lines, $utf8NoBom)
        $written++
    }
}

Write-Output "Generated $written vanilla/server talisman configs in $OutputDirectory"
