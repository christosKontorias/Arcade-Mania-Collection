package com.example.arcademania;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.arcademania.GamesActivityHelper.ActivityGameAdapter;
import com.example.arcademania.GamesActivityHelper.ActivityGameData;
import java.util.ArrayList;


public class GamesFragment extends Fragment implements ActivityGameAdapter.OnItemClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_games, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // Create and populate gameList as a local variable
        ArrayList<ActivityGameData> gameList = new ArrayList<>();
        addDataToList(gameList);

        ActivityGameAdapter gameAdapter = new ActivityGameAdapter(gameList);
        recyclerView.setAdapter(gameAdapter);

        // Set the click listener for the adapter
        gameAdapter.setOnItemClickListener(this);

        // Set up a grid layout with two columns
        int spanCount = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);

        // Calculate the number of games
        int gameCount = gameList.size();

         // Update the game count TextView with the string resource
        String gameCountText = getResources().getString(R.string.game_count_format, gameCount);
        TextView gameCountTextView = rootView.findViewById(R.id.txt_CountGames);
        gameCountTextView.setText(gameCountText);

        return rootView;
    }

    @Override
    public void onItemClick(ActivityGameData gameData) {
        showGameDialog(gameData);
    }

    private void showGameDialog(ActivityGameData gameData) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.game_dialog_layout);

        ImageView closeButton = dialog.findViewById(R.id.closeButton);
        ImageView gameLogoImageView = dialog.findViewById(R.id.dialogGameLogo);
        TextView gameTitleTextView = dialog.findViewById(R.id.dialogGameTitle);
        TextView gameDescriptionTextView = dialog.findViewById(R.id.dialogGameDescription);
        ImageView urlImage = dialog.findViewById(R.id.urlImage);

        gameLogoImageView.setImageResource(gameData.getGameLogo());
        gameTitleTextView.setText(gameData.getGameTitle());
        gameDescriptionTextView.setText(gameData.getGameDescription());

        closeButton.setOnClickListener(view -> dialog.dismiss());

        // Get the URL from the gameData object
        final String gameUrl = gameData.getGameUrl();
        urlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the action to open the URL in a web browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gameUrl));
                view.getContext().startActivity(intent);
            }
        });

        dialog.show();
    }

    private void addDataToList(ArrayList<ActivityGameData> gameList) {
        gameList.add(new ActivityGameData(R.drawable.pac_man_logo, "PacMan", "Pac-Man is a Japanese video game franchise developed, published and owned by Bandai Namco Entertainment, a video game publisher that was previously known as Namco. Entries have been developed by a wide array of other video game companies, including Midway Games, Atari and Mass Media, Inc., and was created by Toru Iwatani. The eponymous first entry was released in arcades in 1980 by Namco, and published by Midway Games in North America. Most Pac-Man games are maze chase games, but it has also delved into other genres, such as platformers, racing, and sports. Several games in the series were released for a multitude of home consoles and are included in many Bandai Namco video game compilations. Pac-Man is one of the longest-running, best-selling, and highest-grossing video game franchises in history, and the game has seen regular releases for over 40 years, has sold nearly 48 million copies across all of the platforms, and has grossed over US$14 billion, most of which has been from the original arcade game. The character of Pac-Man is the official mascot of Bandai Namco, and is one of the most recognizable video game characters in history. The franchise has been seen as important and influential, and is often used as a representation for 1980s popular culture and video games as a whole.", "https://en.wikipedia.org/wiki/List_of_Pac-Man_video_games"));
        gameList.add(new ActivityGameData(R.drawable.space_invaders_logo, "Space Invaders", "Space Invaders is a Japanese shooting video game released in 1978 by Taito. It was developed by Tomohiro Nishikado, who was inspired by other media: Breakout, The War of the Worlds, and Star Wars.It is one of the forerunners of modern video gaming and helped expand the video game industry from a novelty to a global industry. It was first released as an arcade game and later remade on different platforms; re-releases include ported and updated versions. Ported versions generally feature different graphics and additional gameplay options, including moving defense bunkers, zigzag shots, invisible aliens, and two-player modes. Space Invaders is one of the highest-grossing video game franchises of all time.", "https://el.wikipedia.org/wiki/Space_Invaders"));
        gameList.add(new ActivityGameData(R.drawable.galaga_logo, "Galaga", "Galaga is a 1981 fixed shooter arcade video game developed and published by Namco. In North America, it was released by Midway Manufacturing. It is the sequel to Galaxian (1979), Namco's first major video game hit in arcades. Controlling a starship, the player is tasked with destroying the Galaga forces in each stage while avoiding enemies and projectiles. Some enemies can capture a player's ship via a tractor beam, which can be rescued to transform the player into a \"dual fighter\" with additional firepower.", "https://en.wikipedia.org/wiki/Galaga"));
        gameList.add(new ActivityGameData(R.drawable.donkey_kong_logo, "Donkey Kong", "Donkey Kong is a video game franchise created by Shigeru Miyamoto and owned by Nintendo. It follows the adventures of a gorilla, named Donkey Kong, and his family and friends consisting of various primates. The franchise primarily consists of platform games—originally single-screen action puzzle games and later side-scrolling platformers. The first is the 1981 arcade game Donkey Kong, debuting the main antagonist Donkey Kong and the hero Mario, in an industrial construction setting. The game was a massive success and was followed by two sequels released in 1982 and 1983. In 1994, the franchise was relaunched with the platformer Donkey Kong Country, in which Donkey Kong is antagonized by a variety of anthropomorphic enemies, mainly the Kremlings, a clan of crocodiles led by King K. Rool, who has stolen the Kongs' banana hoard.", "https://en.wikipedia.org/wiki/Galaga"));
        gameList.add(new ActivityGameData(R.drawable.frogger_logo, "Frogger", "Frogger is a 1981 arcade action game developed by Konami and manufactured by Sega. In North America, it was released by Sega/Gremlin. The object of the game is to direct a series of frogs to their homes by crossing a busy road and a hazardous river. Frogger was positively received as one of the greatest video games ever made and followed by several clones and sequels. By 2005, 20 million copies of its various home video game incarnations had been sold worldwide. It entered popular culture, including television and music.", "https://en.wikipedia.org/wiki/Frogger"));
        gameList.add(new ActivityGameData(R.drawable.centipede_logo, "Centipede", "Centipede is a 1980 fixed shooter arcade video game developed and published by Atari, Inc. Designed by Dona Bailey and Ed Logg, it was one of the most commercially successful games from the golden age of arcade video games and one of the first with a significant female player base. The primary objective is to shoot all the segments of a centipede that winds down the playing field. An arcade sequel, Millipede, followed in 1982. Centipede was ported to Atari's own Atari 2600, Atari 5200, Atari 7800, and Atari 8-bit family. Under the Atarisoft label, the game was sold for the Apple II, Commodore 64, ColecoVision, VIC-20, IBM PC (as a self-booting disk), Intellivision, and TI-99/4A. Superior Software published the port for the BBC Micro. Versions for the Game Boy and Game Boy Color were also produced, as well as a version for the short-lived Game.com developed by Handheld Games and published by Tiger Electronics.", "https://en.wikipedia.org/wiki/Centipede_(video_game)"));
        gameList.add(new ActivityGameData(R.drawable.asteroids_logo, "Asteroids", "Asteroids is a space-themed multidirectional shooter arcade video game designed by Lyle Rains and Ed Logg released in November 1979 by Atari, Inc. The player controls a single spaceship in an asteroid field which is periodically traversed by flying saucers. The object of the game is to shoot and destroy the asteroids and saucers, while not colliding with either, or being hit by the saucers' counter-fire. The game becomes harder as the number of asteroids increases. Asteroids was conceived during a meeting between Logg and Rains, who decided to use hardware developed by Howard Delman previously used for Lunar Lander. Asteroids was based on an unfinished game titled Cosmos; its physics model, control scheme, and gameplay elements were derived from Spacewar!, Computer Space, and Space Invaders and refined through trial and error. The game is rendered on a vector display in a two-dimensional view that wraps around both screen axes. Asteroids was one of the first major hits of the golden age of arcade games; the game sold 47,840 upright cabinets and 8,725 cocktail cabinets arcade cabinets and proved both popular with players and influential with developers. In the 1980s it was ported to Atari's home systems, and the Atari VCS version sold over three million copies. The game was widely imitated, and it directly influenced Defender, Gravitar, and many other video games.", "https://en.wikipedia.org/wiki/Asteroids_(video_game)"));
        gameList.add(new ActivityGameData(R.drawable.defender_logo, "Defender", "Defender is a horizontally scrolling shooter video game developed by Williams Electronics in 1980 and released for arcades in 1981. A side-scrolling shooter, the game is set on either an unnamed planet or city (depending on platform) where the player must defeat waves of invading aliens while protecting astronauts. Development was led by Eugene Jarvis, a pinball programmer at Williams; Defender was Jarvis' first video game project and drew inspiration from Space Invaders and Asteroids. Defender was demonstrated in late 1980, before entering production in early 1981. It was distributed in Japan by Taito. Defender was one of the most important titles of the golden age of arcade video games, selling over 55,000 units to become the company's best-selling game and one of the highest-grossing arcade games ever. Praise among critics focused on the game's audio-visuals and gameplay. It is frequently listed as one of Jarvis' best contributions to the video game industry and one of the most difficult video games. Though not the first game to scroll horizontally, it created the genre of purely horizontal scrolling shoot 'em ups. It inspired the development of other games and was followed by sequels and many imitations.", "https://en.wikipedia.org/wiki/Defender_(1981_video_game)"));
        gameList.add(new ActivityGameData(R.drawable.street_fighter_ii_logo, "Street Fighter II", "Street Fighter II: The World Warrior is a 2D fighting game developed by Capcom and originally released for arcades in 1991. It is the second installment in the Street Fighter series and the sequel to 1987's Street Fighter. It is Capcom's fourteenth game to use the CP System arcade system board. Street Fighter II vastly improved many of the concepts introduced in the first game, including the use of special command-based moves, a combo system, a six-button configuration, and a wider selection of playable characters, each with a unique fighting style.", "https://en.wikipedia.org/wiki/Street_Fighter_II"));
        gameList.add(new ActivityGameData(R.drawable.tetris_logo, "Tetris", "Tetris (styled TETЯIS) is a puzzle game developed by Atari Games and originally released for arcades in 1988. Based on Alexey Pajitnov's Tetris, Atari Games' version features the same gameplay as the computer editions of the game, as players must stack differently shaped falling blocks to form and eliminate horizontal lines from the playing field. The game features several difficulty levels and two-player simultaneous play.","https://en.wikipedia.org/wiki/Tetris_(Atari_Games)"));
        gameList.add(new ActivityGameData(R.drawable.dig_dug, "Dig Dug", "Dig Dug is a maze arcade video game developed by Namco in 1981 and released in 1982, distributed in North America by Atari, Inc. The player controls Dig Dug to defeat all enemies per stage, by either inflating them to bursting or crushing them underneath rocks. Dig Dug was planned and designed by Masahisa Ikegami, with help from Galaga creator Shigeru Yokoyama. It was programmed for the Namco Galaga arcade board by Shouichi Fukatani, who worked on many of Namco's earlier arcade games, along with Toshio Sakai. Music was composed by Yuriko Keino, including the character movement jingle at executives' request, as her first Namco game. Namco heavily marketed it as a \"strategic digging game\". Upon release, Dig Dug was well received by critics for its addictive gameplay, cute characters, and strategy. During the golden age of arcade video games, it was globally successful, including as the second highest-grossing arcade game of 1982 in Japan. It prompted a long series of sequels and spin-offs, including the Mr. Driller series, for several platforms. It is in many Namco video game compilations for many systems.", "https://en.wikipedia.org/wiki/Dig_Dug"));
        gameList.add(new ActivityGameData(R.drawable.bubble_bobble_logo, "Bubble Bubble", "Bubble Bobble is a 1986 platform game developed and published by Taito for arcades. It was distributed in the United States by Romstar, and in Europe by Electrocoin. Players control Bub and Bob, two dragons that set out to save their girlfriends from a world known as the Cave of Monsters. In each level, Bub and Bob must defeat each enemy present by trapping them in bubbles and popping, who turn into bonus items when they hit the ground. There are 100 levels total, each becoming progressively more difficult. Bubble Bobble was designed by Fukio \"MTJ\" Mitsuji. When he joined Taito in 1986, he felt that Taito's game output was of mediocre quality. In response, he decided to make a game that was fun to play and could rejuvenate the company's presence in the industry. Mitsuji hoped his game would appeal to women, specifically couples that visited arcades. As such, he decided to make Bubble Bobble focus largely on its two player co-operative mode. He made bubbles the core mechanic as he thought they would be a fun element that girls would enjoy. Bubble Bobble became one of Taito's biggest arcade successes, and is credited with inspiring the creation of many similar screen-clear platform games that followed. It was acclaimed by critics for its character design, memorable soundtrack, gameplay, and multiplayer, and is often listed among the greatest games of all time. Bubble Bobble was followed by a long list of sequels and successors for multiple platforms; one of these, Puzzle Bobble, has become successful in its own right and spawned its own line of sequels.", "https://en.wikipedia.org/wiki/Bubble_Bobble"));
        gameList.add(new ActivityGameData(R.drawable.rampage_logo, "Rampage", "Rampage is a 1986 arcade game by Bally Midway. Players take control of a trio of gigantic monsters trying to survive against onslaughts of military forces. Each round is completed when a particular city is completely reduced to rubble. Warner Bros. currently owns all rights to the property via their purchase of Midway Games. Inspired by monster films, Rampage spawned five sequels and a film adaptation in 2018. In the game, players control a trio of monsters: George, Lizzie, and Ralph, humans turned into creatures due to various experimental mishaps. The objective is to destroy cities and combat military forces while maintaining their health. The game is set across 128 days in cities across North America, with each cycle repeating five times. Gameplay includes destroying buildings, eating humans, and avoiding damage.","https://en.wikipedia.org/wiki/Rampage_(1986_video_game)"));
        gameList.add(new ActivityGameData(R.drawable.mortal_kombat_logo, "Mortal Kombat", "Mortal Kombat Arcade Kollection is a 2011 video game co-developed by NetherRealm Studios, Other Ocean Interactive and Code Mystics and published by Warner Bros. Games. It is a compilation of three classic 2D fighting games in the Mortal Kombat series: Mortal Kombat (1992), Mortal Kombat II (1993) and Ultimate Mortal Kombat 3 (1995). The game was created after an ambitious previous incarnation, an HD remake project titled Mortal Kombat HD Arcade Kollection, was cancelled. Arcade Kollection was released as a downloadable title for PlayStation 3 on August 30, 2011, Xbox 360 on August 31, 2011, and for Microsoft Windows on February 1, 2012.","https://en.wikipedia.org/wiki/Mortal_Kombat_Arcade_Kollection"));
        gameList.add(new ActivityGameData(R.drawable.qbert_logo, "Q*bert", "Q*bert (also known as Qbert) is an arcade video game developed and published for the North American market by Gottlieb in 1982. It is a 2D action game with puzzle elements that uses isometric graphics to create a pseudo-3D effect. The objective of each level in the game is to change every cube in a pyramid to a target color by making Q*bert, the on-screen character, hop on top of the cube while avoiding obstacles and enemies. Players use a joystick to control the character. The game was conceived by Warren Davis and Jeff Lee. Lee designed the title character and original concept, which was further developed and implemented by Davis. Q*bert was developed under the project name Cubes.","https://en.wikipedia.org/wiki/Q*bert"));
        gameList.add(new ActivityGameData(R.drawable.double_dragon_logo, "Double Dragon", "Double Dragon is a 1987 beat 'em up video game developed by Technōs Japan and distributed by Taito for arcades across Asia, North America and Europe. It is the first title in the Double Dragon franchise. The game's development was led by Yoshihisa Kishimoto, and it is a spiritual and technological successor to Technos' earlier beat 'em up, Nekketsu Kōha Kunio-kun (1986), released outside of Japan by Taito as Renegade; Kishimoto originally envisioned it as a direct sequel and part of the Kunio-kun series, before making it a new game with a different cast and setting.", "https://en.wikipedia.org/wiki/Double_Dragon_(video_game)"));
    }
}