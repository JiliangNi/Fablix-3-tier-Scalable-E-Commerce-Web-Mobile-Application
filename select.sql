USE `moviedb`;



Select m.title, m.year, m.director, group_concat(distinct g.name), group_concat(distinct s.name), rating
From (Select m.id, m.title, m.year, m.director, r.rating
From movies m, ratings r
where m.id = r.movieId
group by m.id
order by -r.rating
limit 20) as m, genres_in_movies gim, genres g, stars_in_movies sim, stars s
where m.id = gim.movieId and gim.genreId = g.id and m.id = sim.movieId and sim.starId = s.id
group by m.id
order by -rating
limit 20
;

