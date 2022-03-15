thefile = File.open('../../input/day-9')
$input = thefile.read.split
thefile.close

def check_above(i, j)
    if i > 0
        $input[i-1][j] > $input[i][j]
    else
        true
    end
end

def check_below(i, j)
    if i < 4
        $input[i+1][j] > $input[i][j]
    else
        true
    end
end

def check_left(i,j)
    if j > 0
        $input[i][j-1] > $input[i][j]
    else
        true
    end
end

def check_right(i,j)
    if j < 9
        $input[i][j+1] > $input[i][j]
    else
        true
    end
end



def check_lowest(i,j)
    [check_left(i,j), check_right(i,j), check_below(i,j), check_above(i,j)].all?
end


#puts $input.each{|line| puts line.include?("5")}

$lowest_locations = Array.new
for i in 0..4
    for j in 0..9
        if check_lowest(i,j)
            #puts [i,j]
            $lowest_locations.push([i,j])
        end
    end
end


def find_length_of_basin(i,j)
    puts i,j
    if $input[i][j] == '9'
        return 0
    end
    if i.between?(0, 4) and j.between?(0,9)
        return 1 + find_length_of_basin(i-1,j) +
        find_length_of_basin(i+1,j) +
        find_length_of_basin(i,j-1) +
        find_length_of_basin(i,j+1)
    else
        return 0
    end
end

find_length_of_basin(0,1)

#$lowest_locations.each {|point| find_length_of_basin(point)}
