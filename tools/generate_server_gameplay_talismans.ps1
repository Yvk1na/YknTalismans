param(
    [string]$OutputDirectory = (Join-Path (Split-Path -Parent $PSScriptRoot) 'talismans\ykn_server_gameplay')
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$romans = @('I', 'II', 'III', 'IV', 'V')
$cores = @(
    'ecoitems:talisman_core_1 ? ender_eye',
    'ecoitems:talisman_core_1 ? ender_eye',
    'ecoitems:talisman_core_2 ? heart_of_the_sea',
    'ecoitems:talisman_core_2 ? heart_of_the_sea',
    'ecoitems:talisman_core_3 ? nether_star'
)
$rarities = @(
    [pscustomobject]@{ Color = '&8'; Lore = '&8普通护符' }
    [pscustomobject]@{ Color = '&a'; Lore = '&a少见的护符' }
    [pscustomobject]@{ Color = '&9'; Lore = '&9稀有护符' }
    [pscustomobject]@{ Color = '&5'; Lore = '&5史诗护符' }
    [pscustomobject]@{ Color = '&6'; Lore = '&6传说护符' }
    [pscustomobject]@{ Color = '&d'; Lore = '&d神话护符' }
)

# Passive effects are built from effect IDs already proven on this server.
# Themes connect them to the server's major game loops without creating hard
# runtime dependencies on optional gameplay plugins.
$families = @(
    [pscustomobject]@{
        Id = 'island_heart'; Name = '空岛之心'; TierCount = 5; BaseRarity = 1; Base = 'oak_sapling'
        Icons = @('grass_block', 'golden_apple', 'beacon', 'respawn_anchor', 'nether_star')
        Materials = @('stone_bricks', 'gold_block', 'diamond_block', 'netherite_ingot', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'bonus_health'; Arg = 'health'; Values = @('1', '3', '6', '11', '18'); Lore = '最大生命'; LoreValues = @('+1', '+3', '+6', '+11', '+18'); Color = '&c' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'armor'; Arg = 'points'; Values = @('1', '2', '3', '5', '7'); Lore = '护甲值'; LoreValues = @('+1', '+2', '+3', '+5', '+7'); Color = '&9' }
        )
    }
    [pscustomobject]@{
        Id = 'void_anchor'; Name = '虚空之锚'; TierCount = 3; BaseRarity = 2; Base = 'obsidian'
        Icons = @('crying_obsidian', 'respawn_anchor', 'end_crystal')
        Materials = @('obsidian', 'crying_obsidian', 'end_crystal')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'safe_fall_distance'; Arg = 'distance'; Values = @('10', '20', '40'); Lore = '安全坠落距离'; LoreValues = @('+10格', '+20格', '+40格'); Color = '&f' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'armor_toughness'; Arg = 'points'; Values = @('1', '2', '4'); Lore = '护甲韧性'; LoreValues = @('+1', '+2', '+4'); Color = '&b' }
        )
    }
    [pscustomobject]@{
        Id = 'maze_compass'; Name = '迷宫星盘'; TierCount = 2; BaseRarity = 1; Base = 'map'
        Icons = @('compass', 'recovery_compass', 'echo_shard')
        Materials = @('map', 'ender_eye', 'echo_shard')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'movement_speed_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.08'); Lore = '移动速度'; LoreValues = @('+2%', '+5%', '+8%'); Color = '&f' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'add_luck'; Arg = 'amount'; Values = @('1', '2', '4'); Lore = '原版幸运'; LoreValues = @('+1', '+2', '+4'); Color = '&a' }
        )
    }
    [pscustomobject]@{
        Id = 'boss_hunter_mark'; Name = '首领猎印'; TierCount = 5; BaseRarity = 1; Base = 'rotten_flesh'
        Icons = @('bone', 'wither_skeleton_skull', 'nether_star', 'mace', 'dragon_egg')
        Materials = @('bone_block', 'soul_sand', 'wither_skeleton_skull', 'heavy_core', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'damage_multiplier'; Arg = 'multiplier'; Values = @('1.01', '1.03', '1.07', '1.13', '1.21'); Lore = '对首领伤害'; LoreValues = @('+1%', '+3%', '+7%', '+13%', '+21%'); Color = '&c'; BossOnly = $true; Triggers = @('melee_attack', 'bow_attack', 'trident_attack') }
            [pscustomobject]@{ Kind = 'simple'; Id = 'attack_speed_multiplier'; Arg = 'multiplier'; Values = @('1.01', '1.02', '1.04', '1.07', '1.10'); Lore = '攻击速度'; LoreValues = @('+1%', '+2%', '+4%', '+7%', '+10%'); Color = '&e' }
        )
    }
    [pscustomobject]@{
        Id = 'dungeon_lantern'; Name = '地牢魂灯'; TierCount = 4; BaseRarity = 2; Base = 'torch'
        Icons = @('torch', 'soul_lantern', 'sea_lantern', 'totem_of_undying')
        Materials = @('coal_block', 'soul_lantern', 'sea_lantern', 'totem_of_undying')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'armor'; Arg = 'points'; Values = @('1', '2', '4', '7'); Lore = '护甲值'; LoreValues = @('+1', '+2', '+4', '+7'); Color = '&9' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'regen_multiplier'; Arg = 'multiplier'; Values = @('1.04', '1.09', '1.16', '1.28'); Lore = '生命恢复效果'; LoreValues = @('+4%', '+9%', '+16%', '+28%'); Color = '&c' }
        )
    }
    [pscustomobject]@{
        Id = 'artisan_emblem'; Name = '巧匠徽记'; TierCount = 3; BaseRarity = 1; Base = 'crafting_table'
        Icons = @('crafting_table', 'smithing_table', 'crafter')
        Materials = @('iron_block', 'diamond_block', 'netherite_scrap')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'block_reach'; Arg = 'reach'; Values = @('0.25', '0.50', '0.90'); Lore = '方块交互距离'; LoreValues = @('+0.25格', '+0.50格', '+0.90格'); Color = '&e' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'mining_speed_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.09'); Lore = '挖掘速度'; LoreValues = @('+2%', '+5%', '+9%'); Color = '&e' }
        )
    }
    [pscustomobject]@{
        Id = 'reforge_hammer'; Name = '重铸战锤'; TierCount = 4; BaseRarity = 2; Base = 'anvil'
        Icons = @('iron_pickaxe', 'diamond_pickaxe', 'netherite_pickaxe', 'mace')
        Materials = @('anvil', 'diamond_block', 'netherite_ingot', 'heavy_core')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'armor_toughness'; Arg = 'points'; Values = @('1', '2', '4', '6'); Lore = '护甲韧性'; LoreValues = @('+1', '+2', '+4', '+6'); Color = '&b' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'attack_speed_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.08', '1.12'); Lore = '攻击速度'; LoreValues = @('+2%', '+5%', '+8%', '+12%'); Color = '&e' }
        )
    }
    [pscustomobject]@{
        Id = 'merchant_ledger'; Name = '商会秘账'; TierCount = 4; BaseRarity = 2; Base = 'paper'
        Icons = @('paper', 'book', 'written_book', 'nether_star')
        Materials = @('gold_block', 'emerald_block', 'diamond_block', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'sell_multiplier'; Arg = 'multiplier'; Values = @('1.01', '1.03', '1.06', '1.10'); Lore = '商店出售价格'; LoreValues = @('+1%', '+3%', '+6%', '+10%'); Color = '&6' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'villager_trade_multiplier'; Arg = 'multiplier'; Values = @('0.99', '0.97', '0.94', '0.90'); Lore = '村民交易消耗'; LoreValues = @('-1%', '-3%', '-6%', '-10%'); Color = '&a' }
        )
    }
    [pscustomobject]@{
        Id = 'auction_gavel'; Name = '拍卖行槌'; TierCount = 1; BaseRarity = 1; Base = 'gold_ingot'
        Icons = @('wooden_axe', 'golden_axe', 'diamond_axe')
        Materials = @('gold_ingot', 'emerald_block', 'diamond_block')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'add_luck'; Arg = 'amount'; Values = @('1', '2', '4'); Lore = '原版幸运'; LoreValues = @('+1', '+2', '+4'); Color = '&a' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'xp_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.05', '1.10'); Lore = '原版经验获取'; LoreValues = @('+2%', '+5%', '+10%'); Color = '&b' }
        )
    }
    [pscustomobject]@{
        Id = 'quest_scroll'; Name = '冒险任务卷'; TierCount = 3; BaseRarity = 1; Base = 'book'
        Icons = @('paper', 'map', 'enchanted_book')
        Materials = @('book', 'experience_bottle', 'dragon_breath')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'xp_multiplier'; Arg = 'multiplier'; Values = @('1.03', '1.08', '1.15'); Lore = '原版经验获取'; LoreValues = @('+3%', '+8%', '+15%'); Color = '&b' }
            [pscustomobject]@{ Kind = 'simple'; Id = 'movement_speed_multiplier'; Arg = 'multiplier'; Values = @('1.01', '1.03', '1.05'); Lore = '移动速度'; LoreValues = @('+1%', '+3%', '+5%'); Color = '&f' }
        )
    }
    [pscustomobject]@{
        Id = 'collector_codex'; Name = '收藏家图鉴'; TierCount = 5; BaseRarity = 1; Base = 'chest'
        Icons = @('bundle', 'barrel', 'shulker_box', 'ender_chest', 'dragon_egg')
        Materials = @('rabbit_hide', 'gold_ingot', 'shulker_shell', 'ender_chest', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Kind = 'simple'; Id = 'multiply_drops'; Arg = 'multiplier'; Values = @('1.01', '1.03', '1.07', '1.12', '1.20'); Lore = '物品掉落数量'; LoreValues = @('+1%', '+3%', '+7%', '+12%', '+20%'); Color = '&6'; Triggers = @('block_item_drop', 'entity_item_drop', 'catch_fish', 'shear') }
            [pscustomobject]@{ Kind = 'simple'; Id = 'item_magnet'; Arg = 'radius'; Values = @('2', '4', '7', '11', '16'); ExtraArg = 'pull_strength'; ExtraValues = @('0.15', '0.25', '0.40', '0.60', '0.85'); Lore = '物品吸取范围'; LoreValues = @('2格', '4格', '7格', '11格', '16格'); Color = '&a' }
        )
    }
    [pscustomobject]@{
        Id = 'job_badge'; Name = '百业勋章'; TierCount = 4; BaseRarity = 1; Base = 'leather'
        Icons = @('leather_chestplate', 'iron_chestplate', 'diamond_chestplate', 'netherite_chestplate')
        Materials = @('iron_ingot', 'gold_block', 'diamond_block', 'netherite_ingot')
        Effects = @(
            [pscustomobject]@{ Kind = 'skill_xp'; Id = 'skill_xp_multiplier'; Values = @('1.02', '1.05', '1.09', '1.14'); Skills = @('farming', 'foraging', 'mining', 'fishing', 'excavation'); Lore = '生产技能经验'; LoreValues = @('+2%', '+5%', '+9%', '+14%'); Color = '&a' }
            [pscustomobject]@{ Kind = 'add_stat'; Id = 'add_stat'; Stat = 'luck'; Values = @('2', '4', '7', '11'); Lore = '幸运'; LoreValues = @('+2', '+4', '+7', '+11'); Color = '&a' }
        )
    }
    [pscustomobject]@{
        Id = 'class_sigil'; Name = '职业秘印'; TierCount = 5; BaseRarity = 1; Base = 'iron_sword'
        Icons = @('white_banner', 'blue_banner', 'purple_banner', 'black_banner', 'dragon_head')
        Materials = @('iron_block', 'gold_block', 'diamond_block', 'netherite_ingot', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Kind = 'add_stat'; Id = 'add_stat'; Stat = 'strength'; Values = @('2', '4', '8', '14', '22'); Lore = '力量'; LoreValues = @('+2', '+4', '+8', '+14', '+22'); Color = '&c' }
            [pscustomobject]@{ Kind = 'add_stat'; Id = 'add_stat'; Stat = 'toughness'; Values = @('1', '3', '6', '10', '16'); Lore = '韧性'; LoreValues = @('+1', '+3', '+6', '+10', '+16'); Color = '&9' }
        )
    }
    [pscustomobject]@{
        Id = 'arcane_conduit'; Name = '奥术导器'; TierCount = 5; BaseRarity = 1; Base = 'amethyst_shard'
        Icons = @('amethyst_shard', 'end_rod', 'enchanting_table', 'beacon', 'dragon_egg')
        Materials = @('amethyst_block', 'ender_eye', 'experience_bottle', 'beacon', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Kind = 'add_stat'; Id = 'add_stat'; Stat = 'wisdom'; Values = @('2', '4', '8', '14', '22'); Lore = '智慧'; LoreValues = @('+2', '+4', '+8', '+14', '+22'); Color = '&b' }
            [pscustomobject]@{ Kind = 'add_stat'; Id = 'add_stat'; Stat = 'regeneration'; Values = @('1', '2', '4', '7', '11'); Lore = '再生'; LoreValues = @('+1', '+2', '+4', '+7', '+11'); Color = '&a' }
        )
    }
)

New-Item -ItemType Directory -Path $OutputDirectory -Force | Out-Null
Get-ChildItem -LiteralPath $OutputDirectory -File -Filter '*.yml' -ErrorAction SilentlyContinue | Remove-Item -Force

$utf8NoBom = [System.Text.UTF8Encoding]::new($false)
$written = 0

foreach ($family in $families) {
    for ($index = 0; $index -lt $family.TierCount; $index++) {
        $tierNumber = $index + 1
        $id = "$($family.Id)_$tierNumber"
        $rarity = $rarities[$family.BaseRarity + $index]
        $lines = [System.Collections.Generic.List[string]]::new()

        $displayTier = if ($family.TierCount -eq 1) { '' } else { " $($romans[$index])" }
        $lines.Add("name: `"$($rarity.Color)$($family.Name)$displayTier`"")
        $lines.Add('description:')
        foreach ($effect in $family.Effects) {
            $lines.Add("  - `"$($effect.Color)$($effect.Lore) $($effect.LoreValues[$index])`"")
        }
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
        $lines.Add("  - $($cores[$index])")
        $lines.Add("  - $side")
        $lines.Add('')
        $lines.Add("  - $corner")
        $lines.Add("  - $side")
        $lines.Add("  - $corner")
        $lines.Add('effects:')

        foreach ($effect in $family.Effects) {
            $lines.Add("  - id: $($effect.Id)")
            $lines.Add('    args:')
            switch ($effect.Kind) {
                'add_stat' {
                    $lines.Add("      stat: $($effect.Stat)")
                    $lines.Add("      amount: $($effect.Values[$index])")
                }
                'skill_xp' {
                    $lines.Add("      multiplier: $($effect.Values[$index])")
                    $lines.Add('      skills:')
                    foreach ($skill in $effect.Skills) {
                        $lines.Add("        - $skill")
                    }
                }
                default {
                    $lines.Add("      $($effect.Arg): $($effect.Values[$index])")
                    if ($effect.PSObject.Properties.Name -contains 'ExtraArg') {
                        $lines.Add("      $($effect.ExtraArg): $($effect.ExtraValues[$index])")
                    }
                }
            }
            if ($effect.PSObject.Properties.Name -contains 'BossOnly' -and $effect.BossOnly) {
                $lines.Add('    filters:')
                $lines.Add('      is_boss: true')
            }
            if ($effect.PSObject.Properties.Name -contains 'Triggers') {
                $lines.Add('    triggers:')
                foreach ($trigger in $effect.Triggers) {
                    $lines.Add("      - $trigger")
                }
            }
        }
        $lines.Add('conditions: []')

        $target = Join-Path $OutputDirectory "$id.yml"
        [System.IO.File]::WriteAllLines($target, $lines, $utf8NoBom)
        $written++
    }
}

Write-Output "Generated $written server-gameplay talisman configs in $OutputDirectory"
