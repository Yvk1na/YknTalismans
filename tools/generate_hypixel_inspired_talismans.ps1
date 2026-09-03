param(
    [string]$OutputDirectory = (Join-Path (Split-Path -Parent $PSScriptRoot) 'talismans\ykn_hypixel_inspired')
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

# These are original server-balanced designs inspired by representative
# Hypixel accessory lines, not direct copies of Hypixel item data.
$families = @(
    [pscustomobject]@{
        Id = 'feather_legacy'; Name = '落羽传承'; TierCount = 1; BaseRarity = 0; Base = 'feather'
        Icons = @('feather', 'rabbit_foot', 'phantom_membrane')
        Materials = @('feather', 'rabbit_hide', 'phantom_membrane')
        Effects = @(
            [pscustomobject]@{ Id = 'safe_fall_distance'; Arg = 'distance'; Values = @('5', '10', '18'); Lore = '安全坠落距离'; LoreValues = @('+5格', '+10格', '+18格'); Color = '&f' }
        )
    }
    [pscustomobject]@{
        Id = 'potion_affinity'; Name = '炼药亲和'; TierCount = 2; BaseRarity = 0; Base = 'nether_wart'
        Icons = @('nether_wart', 'potion', 'dragon_breath')
        Materials = @('nether_wart', 'glowstone_dust', 'dragon_breath')
        Effects = @(
            [pscustomobject]@{ Id = 'potion_duration_multiplier'; Arg = 'multiplier'; Values = @('1.10', '1.25', '1.50'); Lore = '药水持续时间'; LoreValues = @('+10%', '+25%', '+50%'); Color = '&d' }
        )
    }
    [pscustomobject]@{
        Id = 'magnetic_core'; Name = '磁引核心'; TierCount = 4; BaseRarity = 1; Base = 'redstone'
        Icons = @('compass', 'lodestone', 'recovery_compass', 'nether_star')
        Materials = @('emerald', 'redstone_block', 'lodestone', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Id = 'item_magnet'; Arg = 'radius'; Values = @('4', '8', '12', '16'); ExtraArg = 'pull_strength'; ExtraValues = @('0.25', '0.40', '0.60', '0.80'); Lore = '物品吸取范围'; LoreValues = @('4格', '8格', '12格', '16格'); Color = '&a' }
        )
    }
    [pscustomobject]@{
        Id = 'healing_emblem'; Name = '治愈圣徽'; TierCount = 4; BaseRarity = 0; Base = 'lily_pad'
        Icons = @('lily_pad', 'glistering_melon_slice', 'ghast_tear', 'totem_of_undying')
        Materials = @('lily_pad', 'glistering_melon_slice', 'ghast_tear', 'totem_of_undying')
        Effects = @(
            [pscustomobject]@{ Id = 'regen_multiplier'; Arg = 'multiplier'; Values = @('1.05', '1.12', '1.25', '1.40'); Lore = '生命恢复效果'; LoreValues = @('+5%', '+12%', '+25%', '+40%'); Color = '&c' }
            [pscustomobject]@{ Id = 'bonus_health'; Arg = 'health'; Values = @('1', '2', '4', '7'); Lore = '最大生命'; LoreValues = @('+1', '+2', '+4', '+7'); Color = '&c' }
        )
    }
    [pscustomobject]@{
        Id = 'mineral_charm'; Name = '矿物秘符'; TierCount = 5; BaseRarity = 1; Base = 'amethyst_shard'
        Icons = @('raw_iron', 'raw_gold', 'diamond', 'netherite_scrap', 'nether_star')
        Materials = @('iron_block', 'gold_block', 'diamond_block', 'netherite_ingot', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Id = 'mining_speed_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.04', '1.07', '1.12', '1.20'); Lore = '挖掘速度'; LoreValues = @('+2%', '+4%', '+7%', '+12%', '+20%'); Color = '&e' }
            [pscustomobject]@{ Id = 'add_luck'; Arg = 'amount'; Values = @('1', '2', '4', '7', '12'); Lore = '原版幸运'; LoreValues = @('+1', '+2', '+4', '+7', '+12'); Color = '&2' }
        )
    }
    [pscustomobject]@{
        Id = 'beastmaster_crest'; Name = '驯兽师冠徽'; TierCount = 5; BaseRarity = 1; Base = 'bone'
        Icons = @('leather_horse_armor', 'iron_horse_armor', 'golden_horse_armor', 'diamond_horse_armor', 'goat_horn')
        Materials = @('leather', 'iron_block', 'gold_block', 'diamond_block', 'nether_star')
        Effects = @(
            [pscustomobject]@{ Id = 'damage_multiplier'; Arg = 'multiplier'; Values = @('1.02', '1.04', '1.07', '1.10', '1.15'); Lore = '对首领伤害'; LoreValues = @('+2%', '+4%', '+7%', '+10%', '+15%'); Color = '&c'; BossOnly = $true; Triggers = @('melee_attack', 'bow_attack', 'trident_attack') }
            [pscustomobject]@{ Id = 'bonus_health'; Arg = 'health'; Values = @('1', '2', '3', '5', '8'); Lore = '最大生命'; LoreValues = @('+1', '+2', '+3', '+5', '+8'); Color = '&c' }
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
            $lines.Add("      $($effect.Arg): $($effect.Values[$index])")
            if ($effect.PSObject.Properties.Name -contains 'ExtraArg') {
                $lines.Add("      $($effect.ExtraArg): $($effect.ExtraValues[$index])")
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

Write-Output "Generated $written Hypixel-inspired talisman configs in $OutputDirectory"
