param(
    [string]$OutputDirectory = (Join-Path (Split-Path -Parent $PSScriptRoot) 'talismans\ykn_auraskills_expansion')
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$tiers = @(
    [pscustomobject]@{ Roman = 'I';   Color = '&a'; Rarity = '&a少见的护符'; Level = 10; XpPercent = 2;  Multiplier = '1.02'; Core = 'ecoitems:talisman_core_1 ? ender_eye' }
    [pscustomobject]@{ Roman = 'II';  Color = '&9'; Rarity = '&9稀有护符';   Level = 25; XpPercent = 4;  Multiplier = '1.04'; Core = 'ecoitems:talisman_core_1 ? ender_eye' }
    [pscustomobject]@{ Roman = 'III'; Color = '&5'; Rarity = '&5史诗护符';   Level = 45; XpPercent = 7;  Multiplier = '1.07'; Core = 'ecoitems:talisman_core_2 ? heart_of_the_sea' }
    [pscustomobject]@{ Roman = 'IV';  Color = '&6'; Rarity = '&6传说护符';   Level = 70; XpPercent = 10; Multiplier = '1.10'; Core = 'ecoitems:talisman_core_2 ? heart_of_the_sea' }
    [pscustomobject]@{ Roman = 'V';   Color = '&d'; Rarity = '&d神话护符';   Level = 95; XpPercent = 15; Multiplier = '1.15'; Core = 'ecoitems:talisman_core_3 ? nether_star' }
)

$families = @(
    [pscustomobject]@{
        Id = 'mining_compass'; Name = '矿脉罗盘'; TierCount = 5; Skill = 'mining'; SkillName = '采矿'; Base = 'coal'
        Icons = @('wooden_pickaxe', 'iron_pickaxe', 'golden_pickaxe', 'diamond_pickaxe', 'netherite_pickaxe')
        Materials = @('iron_ingot', 'gold_ingot', 'diamond', 'ancient_debris', 'netherite_ingot')
        Stats = @([pscustomobject]@{ Id = 'luck'; Label = '幸运'; Color = '&2'; Suffix = ''; Values = @(2, 4, 7, 10, 15) })
    }
    [pscustomobject]@{
        Id = 'forest_totem'; Name = '森灵图腾'; TierCount = 4; Skill = 'foraging'; SkillName = '伐木'; Base = 'oak_sapling'
        Icons = @('wooden_axe', 'stone_axe', 'iron_axe', 'diamond_axe', 'netherite_axe')
        Materials = @('oak_log', 'spruce_log', 'jungle_log', 'cherry_log', 'warped_stem')
        Stats = @([pscustomobject]@{ Id = 'regeneration'; Label = '再生'; Color = '&d'; Suffix = ''; Values = @(1, 3, 5, 8, 12) })
    }
    [pscustomobject]@{
        Id = 'harvest_emblem'; Name = '丰收徽记'; TierCount = 5; Skill = 'farming'; SkillName = '农耕'; Base = 'wheat_seeds'
        Icons = @('wheat', 'carrot', 'golden_carrot', 'golden_hoe', 'netherite_hoe')
        Materials = @('wheat', 'carrot', 'golden_carrot', 'hay_block', 'enchanted_golden_apple')
        Stats = @([pscustomobject]@{ Id = 'luck'; Label = '幸运'; Color = '&2'; Suffix = ''; Values = @(2, 4, 7, 10, 15) })
    }
    [pscustomobject]@{
        Id = 'tide_token'; Name = '潮汐之证'; TierCount = 4; Skill = 'fishing'; SkillName = '钓鱼'; Base = 'string'
        Icons = @('fishing_rod', 'nautilus_shell', 'heart_of_the_sea', 'trident', 'conduit')
        Materials = @('cod', 'salmon', 'nautilus_shell', 'prismarine_crystals', 'heart_of_the_sea')
        Stats = @([pscustomobject]@{ Id = 'luck'; Label = '幸运'; Color = '&2'; Suffix = ''; Values = @(2, 4, 7, 10, 15) })
    }
    [pscustomobject]@{
        Id = 'excavator_relic'; Name = '考古圣物'; TierCount = 3; Skill = 'excavation'; SkillName = '挖掘'; Base = 'gravel'
        Icons = @('brush', 'brick', 'amethyst_shard', 'echo_shard', 'recovery_compass')
        Materials = @('flint', 'brick', 'amethyst_shard', 'echo_shard', 'disc_fragment_5')
        Stats = @([pscustomobject]@{ Id = 'luck'; Label = '幸运'; Color = '&2'; Suffix = ''; Values = @(2, 4, 7, 10, 15) })
    }
    [pscustomobject]@{
        Id = 'guardian_bulwark'; Name = '守望壁垒'; TierCount = 5; Skill = 'defense'; SkillName = '防御'; Base = 'cobblestone'
        Icons = @('shield', 'iron_chestplate', 'diamond_chestplate', 'totem_of_undying', 'netherite_chestplate')
        Materials = @('iron_ingot', 'iron_block', 'diamond', 'diamond_block', 'netherite_ingot')
        Stats = @(
            [pscustomobject]@{ Id = 'toughness'; Label = '韧性'; Color = '&9'; Suffix = ''; Values = @(3, 7, 12, 20, 30) }
            [pscustomobject]@{ Id = 'health'; Label = '生命'; Color = '&c'; Suffix = ''; Values = @(5, 10, 18, 30, 45) }
        )
    }
    [pscustomobject]@{
        Id = 'fighter_medal'; Name = '斗士勋章'; TierCount = 5; Skill = 'fighting'; SkillName = '战斗'; Base = 'rotten_flesh'
        Icons = @('stone_sword', 'iron_sword', 'diamond_sword', 'netherite_sword', 'mace')
        Materials = @('iron_ingot', 'blaze_rod', 'diamond', 'heavy_core', 'netherite_ingot')
        Stats = @(
            [pscustomobject]@{ Id = 'strength'; Label = '力量'; Color = '&c'; Suffix = ''; Values = @(3, 7, 12, 20, 30) }
            [pscustomobject]@{ Id = 'crit_damage'; Label = '暴击伤害'; Color = '&4'; Suffix = '%'; Values = @(2, 4, 7, 12, 20) }
        )
    }
    [pscustomobject]@{
        Id = 'alchemy_crucible'; Name = '炼金坩埚'; TierCount = 4; Skill = 'alchemy'; SkillName = '炼金'; Base = 'glass_bottle'
        Icons = @('spider_eye', 'fermented_spider_eye', 'blaze_powder', 'ghast_tear', 'brewing_stand')
        Materials = @('sugar', 'glistering_melon_slice', 'blaze_powder', 'ghast_tear', 'dragon_breath')
        Stats = @(
            [pscustomobject]@{ Id = 'regeneration'; Label = '再生'; Color = '&d'; Suffix = ''; Values = @(1, 3, 5, 8, 12) }
            [pscustomobject]@{ Id = 'wisdom'; Label = '智慧'; Color = '&b'; Suffix = ''; Values = @(2, 5, 9, 15, 24) }
        )
    }
    [pscustomobject]@{
        Id = 'arcane_tome'; Name = '奥术典籍'; TierCount = 4; Skill = 'enchanting'; SkillName = '附魔'; Base = 'paper'
        Icons = @('book', 'bookshelf', 'enchanting_table', 'experience_bottle', 'echo_shard')
        Materials = @('lapis_lazuli', 'lapis_block', 'amethyst_shard', 'experience_bottle', 'echo_shard')
        Stats = @([pscustomobject]@{ Id = 'wisdom'; Label = '智慧'; Color = '&b'; Suffix = ''; Values = @(3, 7, 12, 20, 30) })
    }
    [pscustomobject]@{
        Id = 'agility_badge'; Name = '逐风徽章'; TierCount = 4; Skill = 'agility'; SkillName = '敏捷'; Base = 'feather'
        Icons = @('feather', 'rabbit_foot', 'wind_charge', 'breeze_rod', 'elytra')
        Materials = @('sugar', 'rabbit_foot', 'wind_charge', 'breeze_rod', 'phantom_membrane')
        Stats = @([pscustomobject]@{ Id = 'speed'; Label = '速度'; Color = '&f'; Suffix = ''; Values = @(3, 7, 12, 20, 30) })
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
        $id = "$($family.Id)_$tierNumber"
        $lines = [System.Collections.Generic.List[string]]::new()

        $lines.Add("name: `"$($tier.Color)$($family.Name) $($tier.Roman)`"")
        $lines.Add('description:')
        $lines.Add('  - "&7携带时获得属性加成："')
        foreach ($stat in $family.Stats) {
            $value = $stat.Values[$index]
            $lines.Add("  - `"$($stat.Color)+$value$($stat.Suffix) $($stat.Label)`"")
        }
        $lines.Add("  - `"&a+$($tier.XpPercent)% $($family.SkillName)技能经验`"")
        $lines.Add('  - ""')
        $lines.Add("  - `"&e需要$($family.SkillName)等级 $($tier.Level)`"")
        $lines.Add('  - "&8同系列仅最高阶生效"')
        $lines.Add('  - ""')
        $lines.Add("  - `"$($tier.Rarity)`"")
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
        foreach ($stat in $family.Stats) {
            $value = $stat.Values[$index]
            $lines.Add('  - id: add_stat')
            $lines.Add('    args:')
            $lines.Add("      stat: $($stat.Id)")
            $lines.Add("      amount: $value")
        }
        $lines.Add('  - id: skill_xp_multiplier')
        $lines.Add('    args:')
        $lines.Add("      multiplier: $($tier.Multiplier)")
        $lines.Add('      skills:')
        $lines.Add("        - $($family.Skill)")
        $lines.Add('conditions:')
        $lines.Add('  - id: has_skill_level')
        $lines.Add('    args:')
        $lines.Add("      skill: $($family.Skill)")
        $lines.Add("      level: $($tier.Level)")

        $target = Join-Path $OutputDirectory "$id.yml"
        [System.IO.File]::WriteAllLines($target, $lines, $utf8NoBom)
        $written++
    }
}

Write-Output "Generated $written AuraSkills talisman configs in $OutputDirectory"
