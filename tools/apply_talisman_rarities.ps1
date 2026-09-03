param(
    [string]$TalismanRoot = (Join-Path (Split-Path -Parent $PSScriptRoot) 'talismans')
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$rarities = @(
    [pscustomobject]@{ Color = '&8'; Lore = '&8普通护符' }
    [pscustomobject]@{ Color = '&a'; Lore = '&a少见的护符' }
    [pscustomobject]@{ Color = '&9'; Lore = '&9稀有护符' }
    [pscustomobject]@{ Color = '&5'; Lore = '&5史诗护符' }
    [pscustomobject]@{ Color = '&6'; Lore = '&6传说护符' }
    [pscustomobject]@{ Color = '&d'; Lore = '&d神话护符' }
)

# The base rarity represents effect strength and the materials needed to make
# the first tier. Every numbered upgrade advances exactly one rarity.
$baseRarity = @{
    archery = 1; attack_speed = 1; boss = 2; experience = 1
    immunity = 1; necrosis = 2; poseidon = 2; resistance = 1
    sharpness = 1; strength = 1

    fortune_clover = 2; life_knot = 2; sage_eye = 1
    titan_seal = 2; war_sigil = 2; wind_feather = 1

    life_crystal = 1; iron_guard = 1; adamant_plate = 1
    fleet_anklet = 0; clockwork_core = 1; spring_charm = 0
    landing_rune = 0; diver_pearl = 0; feast_token = 0
    miner_pulse = 0; builder_reach = 1; scholar_badge = 0
    angler_reel = 1; brewer_hourglass = 0; bounty_satchel = 1
    merchant_seal = 2; trader_writ = 1; war_contract = 1

    feather_legacy = 0; potion_affinity = 0; magnetic_core = 1
    healing_emblem = 0; mineral_charm = 1; beastmaster_crest = 1

    island_heart = 1; void_anchor = 2; maze_compass = 1
    boss_hunter_mark = 1; dungeon_lantern = 2; artisan_emblem = 1
    reforge_hammer = 2; merchant_ledger = 2; auction_gavel = 1
    quest_scroll = 1; collector_codex = 1; job_badge = 1
    class_sigil = 1; arcane_conduit = 1
}

$rarityLinePattern = '^\s*-\s*"&[0-9a-f](普通|少见的|稀有|史诗|传说|神话)护符"\s*$'
$utf8NoBom = [System.Text.UTF8Encoding]::new($false)
$written = 0

$files = Get-ChildItem -LiteralPath $TalismanRoot -Recurse -File -Filter '*.yml' |
    Where-Object Name -ne '_example.yml'

foreach ($file in $files) {
    $id = $file.BaseName
    $family = $id
    $tier = 1
    if ($id -match '^(.*)_(\d+)$') {
        $family = $Matches[1]
        $tier = [int]$Matches[2]
    }

    $base = if ($baseRarity.ContainsKey($family)) {
        [int]$baseRarity[$family]
    } elseif ($file.Directory.Name -eq 'ykn_auraskills_expansion') {
        1
    } else {
        1
    }

    $rarityIndex = [Math]::Min($base + $tier - 1, $rarities.Count - 1)
    $rarity = $rarities[$rarityIndex]
    $lines = [System.Collections.Generic.List[string]]::new()
    foreach ($line in [System.IO.File]::ReadAllLines($file.FullName)) {
        if ($line -notmatch $rarityLinePattern) {
            $lines.Add($line)
        }
    }

    for ($index = 0; $index -lt $lines.Count; $index++) {
        if ($lines[$index] -match '^name:\s*"&[0-9a-f](.*)"\s*$') {
            $lines[$index] = "name: `"$($rarity.Color)$($Matches[1])`""
            break
        }
    }

    $descriptionStart = $lines.IndexOf('description:')
    if ($descriptionStart -lt 0) {
        throw "Missing description block: $($file.FullName)"
    }

    $descriptionEnd = $lines.Count
    for ($index = $descriptionStart + 1; $index -lt $lines.Count; $index++) {
        if ($lines[$index] -match '^[A-Za-z][A-Za-z0-9_-]*:') {
            $descriptionEnd = $index
            break
        }
    }

    while ($descriptionEnd -gt $descriptionStart + 1 -and $lines[$descriptionEnd - 1] -eq '  - ""') {
        $lines.RemoveAt($descriptionEnd - 1)
        $descriptionEnd--
    }

    $lines.Insert($descriptionEnd, "  - `"$($rarity.Lore)`"")
    $lines.Insert($descriptionEnd, '  - ""')
    [System.IO.File]::WriteAllLines($file.FullName, $lines, $utf8NoBom)
    $written++
}

Write-Output "Applied rarity to $written talisman configs."
