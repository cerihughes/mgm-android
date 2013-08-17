//
//  MGMWeeklyChart.m
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 10/08/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMWeeklyChart.h"

#import "MGMWeeklyChart+Relationships.h"

@implementation MGMWeeklyChart

@dynamic startDate;
@dynamic endDate;

- (NSOrderedSet*) fetchChartEntries
{
    __block NSOrderedSet* result;
    [self.managedObjectContext performBlockAndWait:^
    {
        result = self.chartEntries;
    }];
    return result;
}

@end